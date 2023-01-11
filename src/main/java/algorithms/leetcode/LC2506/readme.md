# Questions:
- 两个字符串相似，指的是字符集相同
- 求字符串数组中，相似字符串对的个数

# Input:
- 字符串数组 words

# Output:
- 对数

# Constraints:
- 1 <= words.length <= 100
- 1 <= words[i].length <= 100
- words[i] consist of only lowercase English letters.

# Test Case:
- words = ["aba","aabb","abcd","bac","aabc"], return 2
- words = ["aabb","ab","ba"], return 3
- words = ["nba","cba","dba"]， return 0

# Solution1: BitMask + CountMap + Multiplication
- 字符集只有 26，可以用整型数的位移表示一个字符串的字符集
- 对于每个字符串，计算字符集，并
- Time: O(V * E)
- Space: O(V + E)

# Solution1F: Tags
- TODO-Steps
- Time: O(V * E)
- Space: O(V + E)