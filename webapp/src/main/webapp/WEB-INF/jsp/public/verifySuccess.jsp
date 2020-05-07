<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Register for Wegual Account</title>
<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<style type="text/css">
	body {
		background: #dfe7e9;
		font-family: 'Roboto', sans-serif;
	}
    .form-control {
		font-size: 12px;
		transition: all 0.4s;
		box-shadow: none;
	}
	.form-control:focus {
        border-color: #5cb85c;
    }
    .form-control, .btn {
        border-radius: 50px;
		outline: none !important;
    }
	.signup-form {
		width: 380px;
    	margin: 0 auto;
		padding: 30px 0;
	}
    .signup-form form {
		border-radius: 5px;
    	margin-bottom: 10px;
        background: #fff;
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        padding: 20px;
    }
	.signup-form a {
		color: #5cb85c;
	}    
	.signup-form h2 {
		text-align: center;
		font-size: 24px;
		margin: 10px 0 15px;
	}
	.signup-form .hint-text {
		color: #999;
		text-align: center;
		margin-bottom: 20px;
	}
	.signup-form .form-group {
		margin-bottom: 20px;
	}
    .signup-form .btn {        
        font-size: 12px;
		line-height: 16px;
        font-weight: bold;
		text-align: center;
    }
	.signup-btn {
		text-align: center;
		border-color: #999;
		transition: all 0.4s;
	}
	.signup-btn:hover {
		background: #acd1e2;
		opacity: 0.8;
	}
</style>
</head>
<body>
<div class="signup-form">
    <form action="#" method="post">
		<h2>Awesome!</h2>
		<p class="hint-text">You verified your email address successfully. Proceed to login.</p>
		
        <div class="form-group">
            <button class="btn btn-outline-primary btn-lg btn-block signup-btn" onclick="location.href='${loginUrl}'">Login</button>
        </div>
    </form>
</div>
</body>
</html>