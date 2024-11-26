# 🍊 URL Shortener Serverless

![Stack](https://raw.githubusercontent.com/vsantos1711/url-shortener-serverless/main/public/tech.png)

## Description

This project demonstrates how to build a serverless URL shortener using AWS Lambda, API Gateway, and S3 for storage, orchestrated with Terraform. The application consists of two Lambda functions: one to save URLs to S3 and another to retrieve and redirect short URLs to their original destinations. The project is designed to be scalable and efficient using serverless architecture.

## Technologies Used

- **[Java:](https://www.oracle.com/java/)** The primary programming language used to write the Lambda functions.
- **[Terraform:](https://www.terraform.io/)** Infrastructure as Code (IaC) tool to provision and manage AWS services.
- **[AWS Services:](https://aws.amazon.com/)**
  - **[API Gateway:](https://aws.amazon.com/api-gateway/)** Handles HTTP requests, acting as the entry point to the serverless architecture.
  - **[Lambda Functions:](https://aws.amazon.com/lambda/)** Enables serverless compute for efficient and scalable URL processing.
  - **[S3:](https://aws.amazon.com/s3/)** Used to store the original URLs and their shortened counterparts.
  - **[IAM:](https://aws.amazon.com/iam/)** Manages access control and permissions for AWS services.

## System Architecture

![Diagram](https://raw.githubusercontent.com/vsantos1711/url-shortener-serverless/main/public/diagram.png)

## To-do

- [ ] Configure Terraform for complete deployment (Lambda, S3, IAM, API Gateway)
- [ ] Implement security features like rate limiting and authorization
- [ ] Add unit and integration tests for Lambda functions
