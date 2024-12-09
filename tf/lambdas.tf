
resource "aws_lambda_function" "url_shortener" {
  function_name = "url-shortener"
  role          = aws_iam_role.url_shortener_lambda.arn
  handler       = "com.vsantos.urlshortener.Main::handleRequest" 
  runtime       = "java17"
  memory_size   = 512
  timeout       = 30
  filename         = "../url-shortener/target/url-shortener-1.0-SNAPSHOT.jar"
}

resource "aws_lambda_function_url" "url_shortener" {
  function_name = aws_lambda_function.url_shortener.function_name
  authorization_type = "NONE" 

  cors {
    allow_origins = ["*"]
    allow_methods = ["GET", "POST"] 
  }
}


resource "aws_lambda_function" "redirect_short_url" {
  function_name = "redirect-short-url"
  role          = aws_iam_role.url_shortener_lambda.arn
  handler       = "com.vsantos.redirectshorturl.Main::handleRequest" 
  runtime       = "java17"
  memory_size   = 512
  timeout       = 30
  filename         = "../redirect-short-url/target/redirect-short-url-1.0-SNAPSHOT.jar"
}

resource "aws_lambda_function_url" "redirect_short_url" {
  function_name = aws_lambda_function.redirect_short_url.function_name
  authorization_type = "NONE" 

  cors {
    allow_origins = ["*"]
    allow_methods = ["GET", "POST"] 
  }
}