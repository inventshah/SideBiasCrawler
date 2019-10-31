# LD Debate Side Bias Analysis 
A web crawler to pull Lincoln Douglas tournament round results from [tabroom](https://www.tabroom.com). Analyzes the round results through raw counts. Automates running statistics on the data. Used in creating the *Statistical Analysis of Side Bias in Lincoln Douglas Debate* article series published by the [National Symposium for Debate](http://www.nsdupdate.com/).

## Dependencies
For Java: [jsoup](https://jsoup.org/download)

For Python:

```
pip install numpy
pip install pandas
pip install statsmodels
```

## How to Use the Web Crawler
Create a "url.csv" file in the format:

Date | Season | Topic | Tournament | LD Tabroom Results URL
-----|--------|-------|------------|-----------------------

Compile with jsoup jar file: `javac -cp ./jsoup-1.11.3.jar ./*.java`

Running on Mac OS X: `java -cp .:jsoup-1.11.3.jar Main`

Running on Windows: `java -cp .;jsoup-1.11.3.jar Main`

On every run, data pulled from every url in "url.csv" will be parsed and added to the end of "\_raw.csv" and "\_numeric.csv" in raw and summary formats respectively. In addition, "\_topicBias.csv" and "\_tournament.csv" store summary counts for each topic and tournament.

## How to Calculate Statistics
Run: `python statistics.py` 

A new "\_stats.csv" will be created with statistics from the data scraped above. It will use "\_topicBias.csv" and conduct a two-sided one-proportion z-test on the data. The format will be:

Topic | Neg Round Win % | p-value | Neg Ballot Win % | p-value 
------|-----------------|---------|------------------|---------

## Side Bias Article Links
[October 2019](http://nsdupdate.com/2019/a-statistical-analysis-of-side-bias-on-the-2019-september-october-lincoln-douglas-debate-topic-by-sachin-shah/)

[February 2019](http://nsdupdate.com/2019/a-statistical-analysis-of-side-bias-on-the-2019-january-february-lincoln-douglas-debate-topic/)

[November 2018](http://nsdupdate.com/2018/a-statistical-analysis-of-side-bias-on-the-2018-november-december-lincoln-douglas-debate-topic/)

[October 2018](http://nsdupdate.com/2018/a-statistical-analysis-of-side-bias-on-the-2018-september-october-lincoln-douglas-debate-topic-by-sachin-shah/)

## Built with
- Java 7
- Python 3.6