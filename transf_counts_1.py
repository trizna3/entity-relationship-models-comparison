import matplotlib.pyplot as plt; plt.rcdefaults()
import numpy as np
import matplotlib.pyplot as plt

transformation_counts = [0, 0, 2, 1, 2, 1, 1, 1, 4, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 2, 3, 0, 1, 1, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 1, 3, 1, 0, 2, 2, 0, 0, 2, 2, 0, 0, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 2, 1, 1, 0, 0, 1, 0, 0, 2, 0, 2, 0, 0, 1, 1, 2, 0, 1, 2, 0, 1, 0, 0, 0, 1, 2, 2, 0, 1, 2, 2, 1, 1, 1, 2, 1, 0, 0, 0, 2, 0, 1, 2, 1, 3, 1, 0, 0, 0, 1, 2, 2, 1, 2]

objects = ["." for c in transformation_counts]
y_pos = np.arange(len(objects))

plt.bar(y_pos, transformation_counts, alpha=0.5, color='blue')
yticks = list(set(transformation_counts))
plt.yticks(yticks,yticks)

plt.ylabel('Počet očakávaných transformácií')
plt.xlabel('Referenčné výsledky porovnania')
plt.xticks([],[])

plt.grid(axis='y')
plt.show()
