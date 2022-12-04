resource "aws_cloudwatch_metric_alarm" "zerosum" {
  alarm_name                = "More than 5 Carts for 15 min - 1023"
  namespace                 = "1023"
  metric_name               = "number_of_active_carts.value"

  comparison_operator       = "GreaterThanThreshold"
  threshold                 = "5"
  evaluation_periods        = "3"
  period                    = "300"

  statistic                 = "Maximum"

  alarm_description         = "This alarm goes off as soon as there are more than 5 carts in 15 minutes "
  insufficient_data_actions = []
  alarm_actions       = [aws_sns_topic.alarms.arn]
}


resource "aws_sns_topic" "alarms" {
  name = "alarm-topic-${var.candidate_id}"
}

resource "aws_sns_topic_subscription" "user_updates_sqs_target" {
  topic_arn = aws_sns_topic.alarms.arn
  protocol  = "email"
  endpoint  = var.candidate_email
}