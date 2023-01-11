# Questions: 
 - 判断句子中相邻单词的首尾字母是否相同，环状

# Input: 
 - 字符串可能包含多个单词，单空格相隔

# Output:
 - 是否符合要求

# Constraints:
 - 字符串长度不大于 500
 - 字符串只包含大小写字母和空格
 - 首尾无空格

# Test Case:
 - s = "leetcode", return true
 - s = "leetcode exercises sound delightful", return true
 - s = "Leetcode is cool", return false

# Solution1: Split + IndexMod
 - 将字符串用空格分隔成单词数组
 - 检查每个单词的最后一个字母和下一个单词首字母是否相同，如果不同返回false
 - Time: O(N)
 - Space: O(N)

# Solution1F: IterateSpace
 - 遍历每个字符，如果是空格，且左右不同，则放回False
 - 最后判断字符串首尾是否相同
 - Time: O(N)
 - Space: O(1)    