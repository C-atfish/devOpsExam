terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.40.0"
    }
  }
  backend "s3" {
    bucket = "analytics-1023"
    key    = "config/terraform.state"
    region = "eu-west-1"
  }
}