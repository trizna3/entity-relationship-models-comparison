import matplotlib.pyplot as plt; plt.rcdefaults()
import numpy as np
import matplotlib.pyplot as plt

results = [92.86,100.00,66.67,90.91,100.00,100.00,100.00,92.86,100.00,75.00,75.00,84.00,87.50,82.35,60.00,55.00,88.89,88.78,79.17,84.93,83.75,83.33,61.90]
times = [17642,103,8,160,261,102,9,90018,9810,3,4,16,19,359,93301,3101,13,56181,16934,578,21922,467,31630]

plt.title('Mapp.Pairs matched percentage v Time (average per assignment)')

plt.plot(times,results,marker='x',linestyle='None')

plt.ylabel('Average result success (percentage)')
plt.xlabel('Average time (milliseconds)')

plt.grid()
plt.show()
