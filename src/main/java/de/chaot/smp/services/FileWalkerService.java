package de.chaot.smp.services;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import de.chaot.smp.datamodel.AlbumBO;
import de.chaot.smp.datamodel.ArtistBO;
import de.chaot.smp.datamodel.SongBO;
import de.chaot.smp.exceptions.SmpException;

@Service
public class FileWalkerService {

	private final String defaultFolderpath = "/home/valentin/Music";

	private Logger logger = LoggerFactory.getLogger(FileWalkerService.class);

	@Autowired
	private SongService songService;

	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private ArtistService artistService;
	
	/**
	 * imports all files from a folderpath
	 * 
	 * @param folderpath
	 */
	public void importFilesFromFolder(String folderpath) {
		logger.info("call importFilesFromFolder(" + folderpath + ")");

		if (folderpath.isEmpty())
			folderpath = defaultFolderpath;

		File file = new File(folderpath);

		// No folder -> import it
		if (!file.isDirectory()) {
			importFile(folderpath);
		}

		// folder -> scan recursively
		else {
			for (String filepath : file.list()) {
				importFilesFromFolder(folderpath + "/" + filepath);
			}
		}
	}

	/**
	 * imports a single file from a filepath
	 * @param filepath to the file to be imported
	 */
	private void importFile(String filepath) {
		logger.info("call importFile(" + filepath + ")");

		if (filepath.endsWith(".mp3")) {
			String artistName = "defaultValue";
			String albumTitle = "defaultValue";
			String songTitle = "defaultValue";
			int songTrackNumber = 0;
			Mp3File mp3file;
			try {
				mp3file = new Mp3File(filepath);
				if (mp3file.hasId3v2Tag()) {
//					log.info("file has id3v2 tag");
					ID3v2 id3v2Tag = mp3file.getId3v2Tag();
					songTitle = id3v2Tag.getTitle();
					String trackNo = id3v2Tag.getTrack();
					trackNo = trackNo.replace("/", "");
					songTrackNumber = Integer.parseInt(trackNo);
					artistName = id3v2Tag.getArtist();
					if (id3v2Tag.getAlbum() != null)
						albumTitle = id3v2Tag.getAlbum();
				} else if (mp3file.hasId3v1Tag()) {
//					log.info("file has id3v1 tag");
					ID3v1 id3v1Tag = mp3file.getId3v1Tag();
					songTitle = id3v1Tag.getTitle();
//					songTrackNumber = Integer.parseInt(id3v1Tag.getTrack());
					artistName = id3v1Tag.getArtist();
					if (id3v1Tag.getAlbum() != null)
						albumTitle = id3v1Tag.getAlbum();
				} else {
					logger.info("file has neither id3v2Tag nor id3v1Tag");
				}
				SongBO songBO = new SongBO();
				songBO.setFilepath(filepath);
				songBO.setTitle(songTitle);
				songBO.setTrackNumber(songTrackNumber);

				ArtistBO artistBO;
				if (artistService.findByName(artistName).size() > 0) {
					logger.info("found artists with name: " + artistName + " using the first one.");
					artistBO = artistService.findByName(artistName).get(0);
				} else {
					logger.info("Could not find an artist with name: " + artistName + " creating new one.");
					artistBO = new ArtistBO();
					artistBO.setName(artistName);
				}
				artistService.save(artistBO);
				songBO.setArtist(artistBO);

				
				AlbumBO albumBO;
				if (albumService.findByArtistAndTitle(artistBO, albumTitle).size() > 0) {
					logger.info("found albums for: " + artistName + " - " + albumTitle + " using the first one.");
					albumBO = albumService.findByArtistAndTitle(artistBO, albumTitle).get(0);
				} else {
					logger.info("No albums found for: " + artistName + " - " + albumTitle + " creating new one.");
					albumBO = new AlbumBO();
					albumBO.setArtist(artistBO);
					albumBO.setTitle(albumTitle);
				}
				albumService.save(albumBO);
				songBO.setAlbum(albumBO);
				
				songService.save(songBO);
				try {
					albumBO.addSong(songBO);
				} catch (SmpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				albumService.save(albumBO);
				try {
					if (!artistBO.hasAlbum(albumBO)) artistBO.addAlbum(albumBO);
				} catch (SmpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				artistService.save(artistBO);

			} catch (UnsupportedTagException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			logger.info("Not a mp3 file. Skipping.");
	}
}
