# Questions:
- 找出数组中符合XOR[i:j-1]==XOR[j:k]的下标对(i<j<=k)的个数

# Input:
- 数组 arr

# Output:
- 符合要求的下表对的个数

# Constraints:
- 1 <= arr.length <= 300
- 1 <= arr[i] <= 108

# Test Case:
- arr = [2,3,1,6,7], return 4
- arr = [1,1,1,1,1], return 10

# Solution1: Tags
- a = b 相当于 a ^ b = 0
- XOR[i:j-1]和XOR[j:k] 相等,等价于XOR[i:k] = 0
- 假设 Prefix[i] = Prefix[j] = Prefix[k]
- XOR[i + 1:j] = 0, 因为i<x<=j，所以有j-i-1+1-1种可能
- ans[i] = 0 = i * 0 - 0
- ans[j] = j - i - 1 = j * 1 - (i + 1)
- ans[k] = (k - j - 1) + (k - i - 1) = k * 2 - [(i + 1) + (j + 1)] 
- 创建PrefixXOR个数的字典c，赋[0,1],与累计(i + 1)和的字典s
- 遍历下标，更新PrefixXOR，获取对应c和s
- 累加 i*c-s 到结果
- c 加一，s 加 i + 1
- Time: O(N)
- Space: O(N)

