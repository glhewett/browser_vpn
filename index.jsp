<html>
<head>
    <title>browserVPN</title>
    <style src="style/index.css" />
	<script src="index.js" />
</head>
<body>
	<form action="myproxy" method="GET" onSubmit="return fixURL()"/>
		<strong>Enter URL</strong>
		<input type=text name=url value="" size=50 />
		<input type=submit name=action value="Go!" />
	</form>
</body>
</html>