# Questions:
- 给定数组 nums，每加减一次nums[i],开销为cost[i]
- 求将数组所有元素变成一样的最小开销

# Input:
- 数组 nums， 开销数组 cost

# Output:
- 最小开销

# Constraints:
- n == nums.length == cost.length
- 1 <= n <= 10^5
- 1 <= nums[i], cost[i] <= 10^6

# Test Case:
- nums = [1,3,5,2], cost = [2,3,1,14], return 8
- nums = [2,2,2,2,2], cost = [4,2,8,1,3], return 0

# Solution1: BinarySearch + Median
- 二分查找，左闭右开，lo=1, hi=1_000_001
- 计算变成中位数和中位数加一的开销
- 如果前者小，最佳值在前半段，移动右侧
- 如果后者小，最佳值在后半段，移动左侧
- Time: O(N*LogN)
- Space: O(1)

# Solution1: Histgram + PrefixSum
- TODO
