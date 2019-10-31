# Sachin
# September 2019

import csv
import math
import numpy as np
import pandas as pd
from statsmodels.stats import proportion

file = open('_stats.csv', 'w', newline='')
printer = csv.writer(file, delimiter=',', quotechar=' ', quoting=csv.QUOTE_MINIMAL)

# Topic Neg Win %, Pval, Neg Ballot %, Pval,
printer.writerow(["Topic", "Neg Win Percent", "PValue", "Neg Ballot Win Percent", "PValue"])

def percent(s, n):
	return 100 * (s/n)

def one_prop(num, success, results):
	stat, pval = proportion.proportions_ztest(success, num, .5)

	results.append(percent(success, num))
	results.append(pval)

def sequence(data, place):
	results = [place]

	one_prop(data['Rounds'], data['Neg Wins'], results)

	one_prop(data['Ballots'], data['Neg Ballots'], results)

	printer.writerow(results)

data = pd.read_csv('_topicBias.csv', index_col='Topic')
for i in range(len(data)):
	sequence(data.iloc[i], data.index.values[i])