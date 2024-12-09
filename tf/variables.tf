variable "aws_region" {
  description = "The AWS region to deploy resources."
  type        = string
  default     = "us-east-1"
}

variable "aws_profile" {
  description = "The AWS profile to use."
  type        = string
  default     = "adm"
}

variable "bucket_name" {
  description = "The name of the S3 bucket to create."
  type        = string
  default     = "url-shortener-s3bucket"
}