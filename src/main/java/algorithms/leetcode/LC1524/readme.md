# Questions:
- 求子数组和为奇数的个数

# Input:
- 数组 arr

# Output:
- 个数，mod 1_000_000_007

# Constraints:
- 1 <= arr.length <= 10^5
- 1 <= arr[i] <= 100

# Test Case:
- arr = [1,3,5], return 4
- arr = [2,4,6], return 0
- arr = [1,2,3,4,5,6,7], return 16

# Solution1: PrefixSum + Mod + CountArray
- 创建一个数组，分别记录前缀和为奇数和偶数的个数
- 遍历每个元素，更新前缀和的奇偶
  - 偶+子数组=奇，奇+子数组=偶
  - 如果当前前缀和为奇数，以当前元素结尾的子数组就有前缀和为偶数的个数，反之亦然
  - 更新奇偶前缀和的个数
- Time: O(N)
- Space: O(V1)    
