# Questions:
- 操作一，将首字符移动到字符串尾
- 操作二，翻转任意字符
- 求使得字符串相邻字符不同时操作二的最少次数

# Input:
- 二进制字符串 s

# Output:
- 最少翻转次数

# Constraints:
- 1 <= s.length <= 10^5
- s[i] is either '0' or '1'.

# Test Case:
- s = "111000", return 2
- s = "010", return 0
- s = "1110", return 1

# Solution1: SlidingWindow
- 如果字符串为 111000, 将它和下面两个字符串对比，统计差异的个数
-            101010, 总差异数为 a
-            010101, 总差异数为 b
- 从 0 到 n*2 遍历 i
- 更新变成两种情况的差异数
- a += s[i%n] == '0' + i % 2
- b += s[i%n] == '0' + 1 - i % 2
- 如果 i 大于等于 n, 删去左侧的差异数
- a -= s[i-n] == '0' + (i-n) % 2
- b -= s[i-n] == '0' + 1 - (i-n) % 2
- 如果滑动窗口的大小为 n, 将两种差异数更新到结果
- Time: O(N)
- Space: O(1)
