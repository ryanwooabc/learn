# 0000-system-online-judge
- https://medium.com/@saisandeepmopuri/system-design-online-judge-with-data-modelling-40cb2b53bfeb
- https://medium.com/@jnu_saurav/system-design-online-coding-judge-platform-5b39380818fc
- https://iq.opengenus.org/design-of-online-coding-judge/
- https://tianpan.co/notes/243-designing-online-judge-or-leetcode

## Functional Requirement
- view problem, submit solution
- get submissions
- test cases, hidden test cases
- TODO: register, auth
- TODO: edit/view profile
- TODO: leader board of competition
- TODO: problem likes/dislikes/comments/discussions

## Non-functional Requirement
- high reliablity/availability/sacalibity
- low latency

## Capacity Estimatioin
- 5K Questions, 500K Testcases
- 100K concurrent users
- 4 questions * 5 submission * 100K users / 3600 = 500 executions / second
- Inbound: 500 * 1K = 500 KBps
- Outbound: 100K * 1 question * 1K = 100 MBps

## System API
- JSON getQuestion(token, questionId)
    - token: auth, limit rate
    - return { questionId, content }
- JSON submit(token, questionId, solution)
    - return

## Database Design
- users(id, lastName, firstName, email)
- questions(id, title, difficulty, likes, dislikes, content)
- comments(id, targetId, type, userId, content)
- discussions(id, questionId, userId, content)
- submissions(id, userId, questionId, status)
- cases(id, questionId, data)


## High-level Design

- Client ---> LB --> AppServerN --+--> Database(User, Question, TestCases)
  |        |
  +--> SubmissionService ---> MQ ---> Executor ---> Container

## Detail Design

## Teminology