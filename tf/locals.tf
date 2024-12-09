locals {
  lambdas_path = "${path.module}/lambdas"

  common_tags = {
    Project = "Url shortener serverless"
    CreatedAt = formatdate("YYYY-MM-DD", timestamp())
    ManagedBy = "Terraform"
    Owner = "Vinícius Santos"
  }  
}