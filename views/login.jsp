<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/views/header.jsp" />
		
<div id='content'>
	
	<div id="DataEntryPrompt">BrowserVPN</div>
	<form action="Login" method="POST">
	<div id='DataEntryLabel'>Login Form</div>
	<div id='DataEntryDescription'>
		Please Login with your username and password
	</div>
	
	<div id='DataEntryComponent'>
		<div id="DataEntryLabel">Username</div>
		<div id="DataEntryText">
			<input type='text' name='username' value='' />
		</div>
	</div>
	
	<div id='DataEntryComponent'>
		<div id="DataEntryLabel">Password</div>
		<div id="DataEntryText">
			<input type='password' name='password' value='' />
		</div>
	</div>
	
	<div id="DataEntrySubmit">
		<input type="submit" name="action" value="submit" />       
	</div> 
	</form>
</div>

<jsp:include page="/views/footer.jsp" />