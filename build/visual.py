import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

data = pd.read_csv('log.csv').values

point = data.reshape(-1)
plt.plot(np.arange(point.size), point, 'g^')
plt.show()


