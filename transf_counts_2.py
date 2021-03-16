import matplotlib.pyplot as plt; plt.rcdefaults()
import numpy as np
import matplotlib.pyplot as plt

transformation_counts = [0, 0, 2, 1, 2, 1, 1, 1, 4, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 2, 3, 0, 1, 1, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 1, 3, 1, 0, 2, 2, 0, 0, 2, 2, 0, 0, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 2, 1, 1, 0, 0, 1, 0, 0, 2, 0, 2, 0, 0, 1, 1, 2, 0, 1, 2, 0, 1, 0, 0, 0, 1, 2, 2, 0, 1, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 2, 0, 1, 2, 1, 3, 1, 0, 0, 0, 1, 2, 2, 1, 2]

dist_counts = list(set(transformation_counts))
dist_counts.sort()

data = dict()
for count in dist_counts:
    data[count] = len([item for item in transformation_counts if item == count])

plt.bar(dist_counts, [data[count] for count in dist_counts], alpha=0.5, color='blue')

#plt.ylabel('Počet očakávaných transformácií')
#plt.xlabel('Referenčné výsledky porovnania')

yticks = list(range(0,10,2)) + list(range(0,max(data.values())+10,10)) 
plt.yticks(yticks,yticks)

plt.grid(axis='y')
plt.show()
