<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="paginationLink" required="true"%>
<%@ attribute name="totalPages" required="true"%>

<div class="contentAreaInnerContent">
	<ul class="pagination">
		<li class="page-item"><a
			class="page-link" href="${paginationLink}?page=1">First</a></li>
		
			
		<c:if test="${page > 1}">
			<li class="page-item"><a class="page-link"
				href="${paginationLink}?page=${page-1}">${page-1}</a></li>
		</c:if>

		<li class="page-item"><a class="page-link"
			href="${paginationLink}?page=${page}">${page}</a></li>

		<c:if test="${page < totalPages}">
			<li class="page-item"><a class="page-link"
				href="${paginationLink}?page=${page+1}">${page+1}</a></li>
		</c:if>
		
		<li class="page-item"><a class="page-link" href="${paginationLink}?page=${totalPages}">Last</a></li>
	</ul>
</div>
