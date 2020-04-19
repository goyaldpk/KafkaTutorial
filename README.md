# KafkaTutorial

This is a kafka consumer which consumes tweets that are populated by the twitter API Program. Also this seggregates the tweets
based on various categories. These can be tuned according to the need.

To run this, you need to set up twitter API first - https://github.com/goyaldpk/twitter

This also calculates some metrics and those are displayed on grafana dashboard. Graphite with statsd is used as a backend database.

To set up environment 

first we need to setup statsd with graphite. It is very complex. but to ease this a preconfigured docker image can be used. Please
follow this - https://hub.docker.com/r/graphiteapp/docker-graphite-statsd/

graphite dashboard will be available on  - http://localhost/dashboard

Secondly you have to set up grafana for metrics visualization. follow this - https://grafana.com/grafana/download?platform=windows

Now after installing grafana you have to add datasource and panels.

To add Datasource  - https://grafana.com/docs/grafana/latest/features/datasources/add-a-data-source/

The create a dashboard and add queries.

Then run this application as java application. And thats ALL !!!!!!!!
