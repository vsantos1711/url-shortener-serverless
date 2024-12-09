data "aws_iam_policy_document" "lambda_assume_role" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["lambda.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "url_shortener_lambda" {
  name               = "url-shortener-lambda-role"
  assume_role_policy = data.aws_iam_policy_document.lambda_assume_role.json

  tags = local.common_tags
}

data "aws_iam_policy_document" "create_logs_cloudwatch" {
  statement {
    sid     = "AllowCreatingLogGroups"
    effect  = "Allow"
    resources = ["arn:aws:logs:*:*:*"]
    actions = ["logs:CreateLogGroup"]
  }

  statement {
    sid      = "AllowWritingLogs"
    effect   = "Allow"
    resources = ["arn:aws:logs:*:*:log-group:/aws/lambda/*:*"]
    actions  = [
      "logs:CreateLogStream",
      "logs:PutLogEvents"
    ]
  }
}

data "aws_iam_policy_document" "bucket_access" {
  version = "2012-10-17"

  statement {
    sid      = "VisualEditor0"
    effect   = "Allow"
    actions  = [
      "s3:GetObject",
      "s3:PutObject",
      "s3:ListBucket"
    ]
    resources = [
      "arn:aws:s3:::${var.bucket_name}/*",
      "arn:aws:s3:::${var.bucket_name}"
    ]
  }
}

resource "aws_iam_policy" "create_logs_cloudwatch" {
  name   = "create-cloudwatch-logs-policy"
  policy = data.aws_iam_policy_document.create_logs_cloudwatch.json
}

resource "aws_iam_policy" "bucket_access" {
  name   = "bucket-access-policy"
  policy = data.aws_iam_policy_document.bucket_access.json
}

resource "aws_iam_role_policy_attachment" "create_logs_cloudwatch" {
  policy_arn = aws_iam_policy.create_logs_cloudwatch.arn
  role       = aws_iam_role.url_shortener_lambda.name
}

resource "aws_iam_role_policy_attachment" "bucket_access" {
  policy_arn = aws_iam_policy.bucket_access.arn
  role       = aws_iam_role.url_shortener_lambda.name
}
