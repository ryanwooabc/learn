# Questions:
- 找出中位数为K的子数组的个数

# Input:
- 数组 nums，中位数 k

# Output:
- 子数组的个数

# Constraints:
- n == nums.length
- 1 <= n <= 10^5
- 1 <= nums[i], k <= n
- The integers in nums are distinct.

# Test Case:
- nums = [3,2,1,4,5], k = 4, return 3
- nums = [2,3,1], k = 3, return 1

# Solution1: SignArray + PrefixSum + SubarraySum + CountMap
- 将原素组转成正负数组，大于中位数改成1，小于中位数改成-1，否则为0
- 原问题就换成找出和为0或者1的子数组的个数
- PrevPrefixSum + SubArraySum = PrefixSum
- 如果子数组的元素个数为奇数，子数组和为0
- PrefixSum + 0 = PrefixSum
- 如果子数组的元素个数为偶数，中位数是中间两个元素较小的，子数组和为1
- (PrefixSum -1) + 1 = PrefixSum
- 创建前缀和的计数字典，并初始化1一个0
- 遍历每个元素，直到中位数
  - 更新正负数组的前缀和
  - 更新计数字典
- 遇到中位数，前缀和PrefixSum不用更新
- 以中位数结尾，把之前前缀和为PrefixSum和PrefixSum-1的个数累加到结果
- 继续遍历其他元素
  - 更新正负数组的前缀和
  - 统计横跨中位数在子数组，不用更新前缀和的个数
  - 子数组的开头在中位数左侧，结尾在中位数右侧
  - 将子数组和为PrefixSum或者PrefixSum-1的个数累加到结果上
- Time: O(N)
- Space: O(N)