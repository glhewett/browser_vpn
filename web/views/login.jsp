<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/views/header.jsp" />
		
    <div id='content'>
	
	  <div id='prompt'>Please Login</div>
	  <form name="Login" action='Home' method='POST'>
        <div id='component'>
          <div id='label'>Username</div>
          <div id='textfield'>
            <input:text name="username" default=""/>
          </div>
        </div>
        
        <div id='component'>
          <div id='label'>Password</div>
          <div id='textfield'>
            <input:password name="password" default=""/>
          </div>
        </div>
        
        <div id='submit'>
          <input type="submit" name="action" value="Login" />       
        </div>
	  </form>
    </div>

<jsp:include page="/views/footer.jsp" />
