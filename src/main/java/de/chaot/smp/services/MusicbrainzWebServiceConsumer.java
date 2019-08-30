package de.chaot.smp.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.JsonNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicbrainz.mp3.tagger.Tools.Artist;
import com.musicbrainz.mp3.tagger.Tools.Tools;

import de.chaot.smp.datamodel.AlbumBO;
import de.chaot.smp.datamodel.ArtistBO;
import fm.last.musicbrainz.coverart.CoverArt;
import fm.last.musicbrainz.coverart.CoverArtArchiveClient;
import fm.last.musicbrainz.coverart.impl.DefaultCoverArtArchiveClient;

@Service
public class MusicbrainzWebServiceConsumer {

	private final Logger logger = LoggerFactory.getLogger(MusicbrainzWebServiceConsumer.class);

	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private AlbumService albumService;

	public static void performGetRequest() {
		try {
			URL url = new URL(
					"http://musicbrainz.org/ws/2/artist/5b11f4ce-a62d-471e-81fc-a69a8278c7da?inc=aliases&fmt=json");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			int status = con.getResponseCode();
			if (status != 200)
				throw new RuntimeException("HttpResponseCode: " + status);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
				// System.out.println(inputLine);
			}
			in.close();

			JSONParser parse = new JSONParser();
			JSONObject jobj = (JSONObject) parse.parse(content.toString());

			System.out.println(jobj.toString());

			con.disconnect();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getImgForArtist(long id) {
		logger.info("call getImgForArtist(" + id + ")");
		Artist artist = Artist.fetchArtist(getArtistMBIDbyBOID(id));
		logger.info("getting img for:" + artist.getName());
		try {
			if (artist.getImage() != null) {
				logger.info("got an image wikimediaPage: " + artist.getImage());
				return wikimediaToActualURL(artist.getImage());
			}
		} catch (NoSuchElementException e) {
			// TODO
			// e.printStackTrace();
		}
		return "";
	}

	private String wikimediaToActualURL(String image) {
		// TODO Auto-generated method stub

		logger.trace("call wikimediaToActualURL(" + image + ")");
		String[] parts = image.split("File:");
		String part1 = parts[1]; // 034556
		logger.info(part1);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("http://upload.wikimedia.org/wikipedia/commons/");
		String md5 = DigestUtils.md5Hex(part1);
		stringBuilder.append(md5.substring(0, 1));
		stringBuilder.append("/");
		stringBuilder.append(md5.substring(0, 2));
		stringBuilder.append("/");
		stringBuilder.append(part1);

		logger.info(stringBuilder.toString());
		return stringBuilder.toString();
	}

	public String getArtistMBIDbyBOID(long boid) {
		logger.trace("call getArtistMBIDbyBOID(" + boid + ")");
		ArtistBO artistBO = artistService.findById(boid);
		logger.info("artistBO: " + artistBO.getName());
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("http://musicbrainz.org/ws/2/artist/");
		stringBuilder.append("?query=%22artist:\\%22");
		stringBuilder.append(translateSpaces(artistBO.getName()));
		stringBuilder.append("\\%22&limit=1&fmt=json");
		logger.info("query: " + stringBuilder.toString());
		JsonNode jsonNode;
		String res = Tools.httpGet(stringBuilder.toString());
		jsonNode = Tools.jsonToNode(res);
		logger.info("have a result: " + res);
		JsonNode jsonNodeSubNode = jsonNode.get("artists").get(0).get("id");
		String mbid = jsonNodeSubNode.toString();
		mbid = mbid.substring(1, mbid.length() - 1);
		logger.info("subresult: " + mbid);
		return mbid;
	}

	private Object translateSpaces(String name) {
		// TODO Auto-generated method stub
		return name.replace(" ", "_");
	}

	public String getAlbumMBIDbyBOID(long boid) {
		logger.trace("call getAlbumMBIDbyBOID(" + boid + ")");
		AlbumBO albumBO = albumService.findById(boid);
		logger.info("albumBO: " + albumBO.getTitle());
		String artistName = albumBO.getArtist().getName();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("http://musicbrainz.org/ws/2/release/");
		stringBuilder.append("?query=release:\\%22");
		stringBuilder.append(translateSpaces(albumBO.getTitle()));
		stringBuilder.append("\\%22%20AND%20");
		stringBuilder.append("artist:\\%22");
		stringBuilder.append(translateSpaces(artistName));
		stringBuilder.append("\\%22&limit=1&fmt=json");
		logger.info("query: " + stringBuilder.toString());
		
		JsonNode jsonNode;
		String res = Tools.httpGet(stringBuilder.toString());
		jsonNode = Tools.jsonToNode(res);
		logger.info("have a result: " + res);
		if (jsonNode.get("releases").get(0) != null) {
			JsonNode jsonNodeSubNode = jsonNode.get("releases").get(0).get("id");
			String mbid = jsonNodeSubNode.toString();
			mbid = mbid.substring(1, mbid.length() - 1);
			logger.info("subresult: " + mbid);
			return mbid;
		}
		return "404";
	}

	public String getImgForAlbum(int id) {
		CoverArtArchiveClient client = new DefaultCoverArtArchiveClient();
		if (!getAlbumMBIDbyBOID(id).equals("404")) {
			UUID mbid = UUID.fromString(getAlbumMBIDbyBOID(id));
			try {
				CoverArt coverArt = null;
				coverArt = client.getByMbid(mbid);
				if (coverArt != null) {
					return coverArt.getFrontImage().getImageUrl();
				}
			} catch (NoSuchElementException ex) {
				ex.printStackTrace();
			}
		}
		return "";
	}
}
