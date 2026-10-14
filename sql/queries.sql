/*
PROBLEM: Write a SQL query to get the 2nd highest salary from the Employee table.
If there is no 2nd highest salary, return NULL.

TABLE: Employee (Id, Salary)
*/

-- Step 2: Select the salary from our temporary result set (CTE)
SELECT
    Salary
FROM (
         -- Step 1: Create an inner query to rank the salaries
         SELECT
             Salary, -- Select the salary column
             -- DENSE_RANK() assigns a rank to each row based on Salary.
             -- 'ORDER BY Salary DESC' means the highest salary gets rank 1.
             -- DENSE_RANK ensures no gaps in ranking if there are duplicates (e.g., 1, 1, 2).
             DENSE_RANK() OVER (ORDER BY Salary DESC) as salary_rank
         FROM
             Employee
     ) ranked_salaries -- Give an alias to this inner query
-- Step 3: Filter the outer query to only get the row where rank is 2
WHERE
    salary_rank = 2;




/*
PROBLEM: The Employee table holds all employees including their managers.
Every employee has an Id, and there is also a column for the manager Id.
Write a SQL query to find employees who earn more than their managers.

TABLE: Employee (Id, Name, Salary, ManagerId)
*/

-- Step 1: Select the Name of the employee and alias it as 'Employee' for the output
SELECT
    e.Name AS Employee
FROM
    -- Step 2: Query the Employee table and alias it as 'e' (representing the worker)
    Employee e
-- Step 3: Join the SAME Employee table to itself, aliasing the second one as 'm' (representing the manager)
        JOIN
    Employee m
-- Step 4: Define the relationship: The worker's ManagerId must match the manager's Id
    ON
        e.ManagerId = m.Id
-- Step 5: Filter the results where the worker's salary is strictly greater than the manager's salary
WHERE
    e.Salary > m.Salary;





/*
PROBLEM: Given a Transactions table, calculate the cumulative sum (running total)
of the transaction amounts ordered by the transaction date.

TABLE: Transactions (TransactionDate, Amount)
*/

-- Step 1: Select the TransactionDate and Amount columns
SELECT
    TransactionDate,
    Amount,
    -- Step 2: Use the SUM() aggregate function, but turn it into a Window Function using 'OVER()'
    SUM(Amount) OVER (
        -- Step 3: ORDER BY TransactionDate tells the database to calculate the sum sequentially
        -- from the earliest date down to the current row's date.
        ORDER BY TransactionDate
    ) AS RunningBalance -- Alias the resulting column
FROM
    Transactions
-- Step 4: Ensure the final output is sorted chronologically
ORDER BY
    TransactionDate;



/*
PROBLEM: Write a SQL query to find employees who have the highest salary in each of the departments.

TABLES:
Employee (Id, Name, Salary, DepartmentId)
Department (Id, Name)
*/

-- Step 1: We use a Common Table Expression (CTE) named 'RankedEmployees' to make the query cleaner
WITH RankedEmployees AS (
    -- Step 2: Inside the CTE, select department ID, employee name, and salary
    SELECT
        DepartmentId,
        Name AS EmployeeName,
        Salary,
        -- Step 3: Use RANK() to rank salaries.
        -- 'PARTITION BY DepartmentId' resets the rank counter for each department.
        -- 'ORDER BY Salary DESC' ensures the highest salary gets rank 1 within that department.
        RANK() OVER (PARTITION BY DepartmentId ORDER BY Salary DESC) as rnk
    FROM
        Employee
)
-- Step 4: Now query from the CTE we just created
SELECT
    d.Name AS Department,    -- Select the department name from the joined table
    re.EmployeeName AS Employee, -- Select the employee name from the CTE
    re.Salary AS Salary      -- Select the salary from the CTE
FROM
    RankedEmployees re
-- Step 5: Join the actual Department table to get the text name of the department
        JOIN
    Department d
    ON
        re.DepartmentId = d.Id
-- Step 6: Filter for rank = 1, which guarantees we only get the highest earners per department
WHERE
    re.rnk = 1;


/*
PROBLEM: Find the IDs of users who logged in for 3 or more consecutive days.

TABLE: Logins (UserId, LoginDate)
Note: Assume LoginDate has no time component (just YYYY-MM-DD) and is unique per user per day.
*/

-- Step 1: Create a CTE to fetch the current date and the dates of the next two logins
WITH NextLogins AS (
    SELECT
        UserId,
        LoginDate,
        -- Step 2: LEAD(LoginDate, 1) grabs the LoginDate from the NEXT row (offset of 1)
        -- PARTITION BY UserId ensures we only look at the current user's dates
        -- ORDER BY LoginDate ensures the rows are chronological
        LEAD(LoginDate, 1) OVER (PARTITION BY UserId ORDER BY LoginDate) AS next_day,

        -- Step 3: LEAD(LoginDate, 2) grabs the LoginDate from TWO rows ahead (offset of 2)
        LEAD(LoginDate, 2) OVER (PARTITION BY UserId ORDER BY LoginDate) AS day_after_next
    FROM
        Logins
)
-- Step 4: Select distinct user IDs to avoid duplicates if a user has a 4-day streak
SELECT DISTINCT
    UserId
FROM
    NextLogins
-- Step 5: Check if the dates are exactly 1 and 2 days apart using standard SQL interval math
-- In PostgreSQL/Oracle, adding an integer to a date adds that many days.
WHERE
    next_day = LoginDate + 1
  AND
    day_after_next = LoginDate + 2;




/*
PROBLEM: Calculate the 7-day rolling average of daily transaction volumes.
Assume the table has one row per day.

TABLE: DailyMetrics (MetricDate, TotalVolume)
*/

-- Step 1: Select the date and the daily volume
SELECT
    MetricDate,
    TotalVolume,
    -- Step 2: Use the AVG() function as a Window Function using the OVER() clause.
    AVG(TotalVolume) OVER (
        -- Step 3: Order the rows chronologically by date.
        ORDER BY MetricDate
        -- Step 4: Define the 'Window Frame'.
        -- 'ROWS BETWEEN 6 PRECEDING AND CURRENT ROW' tells the database to calculate
        -- the average using the current row AND the 6 rows immediately before it (7 days total).
        ROWS BETWEEN 6 PRECEDING AND CURRENT ROW
    ) AS Rolling7DayAvg
FROM
    DailyMetrics
-- Step 5: Order the final result set chronologically.
ORDER BY
    MetricDate;



/*
PROBLEM: Write a query to find the failure rate of payments per day.
The failure rate is the number of 'Failed' payments divided by the total number
of payments for that day. Round the result to 2 decimal places.

TABLE: Payments (Id, PaymentDate, Status)
-- Status can be 'Completed', 'Failed', or 'Pending'
*/

-- Step 1: Group the output by the payment date
SELECT
    PaymentDate,
    -- Step 2: Calculate the failure rate.
    -- ROUND(..., 2) will round the final decimal to 2 places.
    ROUND(
        -- Step 3: Use SUM with a CASE statement. If the status is 'Failed', count it as 1.
        -- Otherwise, count it as 0. This gives us the total number of failed payments.
            SUM(CASE WHEN Status = 'Failed' THEN 1 ELSE 0 END)
                -- Step 4: Divide the failed count by the total count of rows for that day.
                -- Note: We multiply by 1.0 (or cast to DECIMAL) to prevent integer division
                -- (which would result in 0 instead of a fraction in databases like PostgreSQL).
                / (COUNT(*) * 1.0),
            2) AS FailureRate
FROM
    Payments
-- Step 5: Group all records by the PaymentDate so the aggregates apply per day
GROUP BY
    PaymentDate
-- Step 6: Order chronologically
ORDER BY
    PaymentDate;





/*
PROBLEM: Write a query to identify all duplicate emails, keeping only the one
with the lowest Id. (Often asked as a DELETE query, but implemented here as a SELECT
that filters out the duplicates for safety).

TABLE: Users (Id, Email, CreatedAt)
*/

-- Step 1: Create a CTE to assign a row number to every record
WITH RankedUsers AS (
    SELECT
        Id,
        Email,
        CreatedAt,
        -- Step 2: ROW_NUMBER() assigns a unique sequential integer to rows.
        -- PARTITION BY Email: This creates a separate numbering sequence for EACH unique email.
        -- ORDER BY Id ASC: This ensures the row with the lowest Id gets row number 1.
        ROW_NUMBER() OVER(PARTITION BY Email ORDER BY Id ASC) as row_num
    FROM
        Users
)
-- Step 3: Select the desired columns from our CTE
SELECT
    Id,
    Email,
    CreatedAt
FROM
    RankedUsers
-- Step 4: Filter out the duplicates.
-- Any row with a row_num > 1 is a duplicate (because the lowest Id was assigned 1).
WHERE
    row_num = 1;

/*
Note: If asked to DELETE the duplicates, the query would look like:
DELETE FROM Users WHERE Id NOT IN (
    SELECT MIN(Id) FROM Users GROUP BY Email
);
*/




/*
PROBLEM: You have a table of monthly revenue. Write a query to pivot the data
so that there is one row per Department, and columns for 'Jan_Revenue', 'Feb_Revenue',
and 'Mar_Revenue'.

TABLE: Revenue (DepartmentId, Month, Amount)
-- Month is a string like 'Jan', 'Feb', 'Mar'
*/

-- Step 1: Select the DepartmentId as our base grouping column
SELECT
    DepartmentId,

    -- Step 2: Create the January column.
    -- If the month is 'Jan', take the amount. Otherwise, take 0.
    -- SUM() aggregates these amounts for the whole department.
    SUM(CASE WHEN Month = 'Jan' THEN Amount ELSE 0 END) AS Jan_Revenue,

    -- Step 3: Repeat the logic for February
    SUM(CASE WHEN Month = 'Feb' THEN Amount ELSE 0 END) AS Feb_Revenue,

    -- Step 4: Repeat the logic for March
    SUM(CASE WHEN Month = 'Mar' THEN Amount ELSE 0 END) AS Mar_Revenue

FROM
    Revenue
-- Step 5: Group by DepartmentId so the SUM() function collapses the rows per department
GROUP BY
    DepartmentId;



/*
PROBLEM: Write a query to return each user's ID, the date of their very first transaction,
and the date of their most recent transaction.

TABLE: Transactions (TransactionId, UserId, TransactionDate, Amount)
*/

-- APPROACH 1: Using standard GROUP BY (Best for simple Min/Max dates)
-- Step 1: Select UserId and use MIN/MAX aggregates
SELECT
    UserId,
    MIN(TransactionDate) AS FirstTransactionDate,
    MAX(TransactionDate) AS LastTransactionDate
FROM
    Transactions
GROUP BY
    UserId;

/*
APPROACH 2: Using Window Functions (Required if you need to return OTHER columns
from that first/last row, such as the actual 'Amount' of their first transaction).
*/

-- Step 1: Create a CTE to rank transactions chronologically and reverse-chronologically
WITH RankedTransactions AS (
    SELECT
        UserId,
        TransactionDate,
        Amount,
        -- Step 2: Rank 1 will be the OLDEST transaction for this user
        ROW_NUMBER() OVER(PARTITION BY UserId ORDER BY TransactionDate ASC) as ascending_rank,
        -- Step 3: Rank 1 will be the NEWEST transaction for this user
        ROW_NUMBER() OVER(PARTITION BY UserId ORDER BY TransactionDate DESC) as descending_rank
    FROM
        Transactions
)
-- Step 4: Select from the CTE where the transaction is either the first or the last
SELECT
    UserId,
    TransactionDate,
    Amount,
    -- Step 5: Use a CASE statement to label the row for the output report
    CASE
        WHEN ascending_rank = 1 THEN 'First Transaction'
        WHEN descending_rank = 1 THEN 'Last Transaction'
        END AS TransactionType
FROM
    RankedTransactions
-- Step 6: Filter to ONLY include the first and last rows
WHERE
    ascending_rank = 1 OR descending_rank = 1
ORDER BY
    UserId, TransactionDate;





/*
PROBLEM: Write a query to find the 3rd transaction made by every user.
If a user has fewer than 3 transactions, they should not appear in the results.

TABLE: Transactions (TransactionId, UserId, TransactionDate, Amount)
*/

-- Step 1: Create a CTE to assign a sequential row number to each user's transactions
WITH NumberedTransactions AS (
    SELECT
        TransactionId,
        UserId,
        TransactionDate,
        Amount,
        -- Step 2: ROW_NUMBER() assigns 1, 2, 3...
        -- PARTITION BY UserId ensures the numbering restarts at 1 for every new user.
        -- ORDER BY TransactionDate ASC ensures the 1st row is their earliest transaction.
        ROW_NUMBER() OVER(PARTITION BY UserId ORDER BY TransactionDate ASC) as txn_number
    FROM
        Transactions
)
-- Step 3: Query the CTE
SELECT
    UserId,
    TransactionId,
    TransactionDate,
    Amount
FROM
    NumberedTransactions
-- Step 4: Filter strictly for the 3rd transaction
WHERE
    txn_number = 3;






/*
PROBLEM: For each transaction, calculate the difference in the amount
compared to that SAME user's previous transaction.
If it is their first transaction, the difference should be NULL or 0.

TABLE: Transactions (TransactionId, UserId, TransactionDate, Amount)
*/

-- Step 1: Select the base columns
SELECT
    TransactionId,
    UserId,
    TransactionDate,
    Amount,

    -- Step 2: Use LAG() to fetch the 'Amount' from the PREVIOUS row (offset of 1).
    -- PARTITION BY UserId ensures we only look at the current user's history.
    LAG(Amount, 1) OVER(PARTITION BY UserId ORDER BY TransactionDate ASC) AS PreviousAmount,

    -- Step 3: Calculate the difference directly in the SELECT clause.
    -- If LAG() returns NULL (because it's the first transaction), the math results in NULL.
    Amount - LAG(Amount, 1) OVER(PARTITION BY UserId ORDER BY TransactionDate ASC) AS AmountDifference

FROM
    Transactions
ORDER BY
    UserId, TransactionDate;



/*
PROBLEM: You have a table of Transaction IDs that are supposed to increment by 1.
Find all the missing IDs in the sequence between the minimum and maximum ID in the table.

TABLE: Ledger (Id, Status)
Assume Id = [1, 2, 4, 5]. The query should return [3].
*/

-- Step 1: Create a recursive CTE to generate a complete sequence of numbers
WITH RECURSIVE SequenceGenerator AS (
    -- Step 2: Anchor member (The starting point). Get the minimum ID from the table.
    SELECT MIN(Id) as ExpectedId
    FROM Ledger

    UNION ALL

    -- Step 3: Recursive member. Keep adding 1 to the ExpectedId...
    SELECT ExpectedId + 1
    FROM SequenceGenerator
    -- Step 4: ...until we hit the maximum ID in the table.
    WHERE ExpectedId < (SELECT MAX(Id) FROM Ledger)
)
-- Step 5: Now we have a perfect sequence (e.g., 1, 2, 3, 4, 5).
-- Select the IDs from our generated sequence...
SELECT
    ExpectedId AS MissingId
FROM
    SequenceGenerator
-- Step 6: ...where that ID does NOT exist in the actual Ledger table.
WHERE
    ExpectedId NOT IN (SELECT Id FROM Ledger);





/*
PROBLEM: Calculate the month-over-month user retention rate.
Specifically, find the number of users active in 'January' who were also active in 'February'.

TABLE: UserActivity (UserId, ActivityDate)
*/

-- Step 1: Create a CTE to extract just the Year and Month for each activity, removing duplicates.
-- We only care IF they were active in a month, not how many times.
WITH MonthlyActiveUsers AS (
    SELECT DISTINCT
        UserId,
        -- Extract the month and year (Syntax varies by DB, this is standard PostgreSQL/MySQL)
        DATE_TRUNC('month', ActivityDate) AS ActivityMonth
    FROM
        UserActivity
)
-- Step 2: Select the month we are evaluating (January)
SELECT
    m1.ActivityMonth AS CohortMonth,
    -- Step 3: Count total users active in January
    COUNT(DISTINCT m1.UserId) AS Jan_Active_Users,
    -- Step 4: Count users who ALSO have a record in February (via the INNER JOIN)
    COUNT(DISTINCT m2.UserId) AS Retained_In_Feb
FROM
    -- Step 5: The base table is January activity
    MonthlyActiveUsers m1
-- Step 6: LEFT JOIN back to the same table to find February activity for the SAME user
        LEFT JOIN
    MonthlyActiveUsers m2
    ON m1.UserId = m2.UserId
        -- And the date in m2 is exactly 1 month AFTER the date in m1
        AND m2.ActivityMonth = m1.ActivityMonth + INTERVAL '1 month'
-- Step 7: Filter the base table to only look at January
WHERE
    m1.ActivityMonth = '2026-01-01'
GROUP BY
    m1.ActivityMonth;





/*
PROBLEM: Write a query to find the employees who are high earners in each of the departments.
A high earner is someone who has a salary in the top 3 unique salaries for that department.

TABLES:
Employee (Id, Name, Salary, DepartmentId)
Department (Id, Name)
*/

-- Step 1: Create a CTE to rank salaries within each department
WITH RankedSalaries AS (
    SELECT
        DepartmentId,
        Name AS EmployeeName,
        Salary,
        -- Step 2: Use DENSE_RANK(), NOT ROW_NUMBER().
        -- If two people earn $100k (the highest), they BOTH get Rank 1.
        -- The next highest salary ($90k) gets Rank 2.
        -- ROW_NUMBER() would incorrectly give the $90k earner Rank 3.
        DENSE_RANK() OVER (PARTITION BY DepartmentId ORDER BY Salary DESC) as salary_rank
    FROM
        Employee
)
-- Step 3: Query the CTE
SELECT
    d.Name AS Department,
    rs.EmployeeName AS Employee,
    rs.Salary AS Salary
FROM
    RankedSalaries rs
        JOIN
    Department d
    ON
        rs.DepartmentId = d.Id
-- Step 4: Filter to only include those in the top 3 ranks
WHERE
    rs.salary_rank <= 3
ORDER BY
    Department, Salary DESC;



/*
PROBLEM: Write a query to find all users who have overlapping active sessions.
A session overlaps if a subsequent session starts BEFORE the previous session ends.

TABLE: UserSessions (SessionId, UserId, StartTime, EndTime)
*/

-- Step 1: Create a CTE to fetch the "next" session's start time for each user
WITH NextSessions AS (
    SELECT
        SessionId,
        UserId,
        StartTime,
        EndTime,
        -- Step 2: Use LEAD() to peek at the StartTime of the very next row.
        -- We partition by UserId to only compare sessions for the same user.
        -- We order by StartTime to ensure they are chronologically aligned.
        LEAD(StartTime, 1) OVER (PARTITION BY UserId ORDER BY StartTime) AS NextStartTime
    FROM
        UserSessions
)
-- Step 3: Query the CTE
SELECT DISTINCT
    UserId
FROM
    NextSessions
-- Step 4: The core overlapping logic.
-- If the current session's EndTime is strictly greater than the next session's StartTime,
-- they are actively overlapping.
WHERE
    EndTime > NextStartTime;






/*
PROBLEM: Given a table of days a user logged in, group the consecutive days together
and return the StartDate and EndDate of each continuous "streak".

TABLE: UserLogins (UserId, LoginDate)
Assume LoginDate is unique per user per day.
*/

-- Step 1: Create a CTE to assign a row number and subtract it from the date
WITH GroupedDays AS (
    SELECT
        UserId,
        LoginDate,
        -- Step 2: TRICK - If dates are consecutive (e.g., Jan 1, Jan 2, Jan 3)
        -- and row numbers are consecutive (1, 2, 3),
        -- subtracting the row number from the date yields the EXACT SAME base date for the whole streak!
        -- (e.g., Jan 2 minus 2 days = Dec 31. Jan 3 minus 3 days = Dec 31).
        LoginDate - CAST(ROW_NUMBER() OVER (PARTITION BY UserId ORDER BY LoginDate) AS INT) AS StreakGroup
    FROM
        UserLogins
)
-- Step 3: Group by this newly discovered "StreakGroup" identifier
SELECT
    UserId,
    -- Step 4: The minimum date in the group is the start of the streak
    MIN(LoginDate) AS StreakStartDate,
    -- Step 5: The maximum date in the group is the end of the streak
    MAX(LoginDate) AS StreakEndDate,
    -- Step 6: Count the days in the streak
    COUNT(*) AS StreakLength
FROM
    GroupedDays
GROUP BY
    UserId,
    StreakGroup
ORDER BY
    UserId,
    StreakStartDate;


/*
PROBLEM: Given an Employee table, write a query to find ALL direct and indirect
reports for a specific manager (e.g., ManagerId = 1).
This requires traversing down the reporting tree.

TABLE: Employee (Id, Name, ManagerId)
*/

-- Step 1: Initialize the RECURSIVE CTE
WITH RECURSIVE OrgChart AS (
    -- Step 2: THE ANCHOR MEMBER
    -- This is the starting point. We select the specific manager (Id = 1)
    -- and set their hierarchical 'Level' to 0.
    SELECT
        Id,
        Name,
        ManagerId,
        0 AS HierarchyLevel
    FROM
        Employee
    WHERE
        Id = 1 -- Replace with target Top-Level Manager ID

    UNION ALL

    -- Step 3: THE RECURSIVE MEMBER
    -- This joins the actual Employee table against the CTE itself!
    SELECT
        e.Id,
        e.Name,
        e.ManagerId,
        oc.HierarchyLevel + 1 -- Increment the level for each step down the tree
    FROM
        Employee e
            -- Step 4: Join condition matches the employee's ManagerId to the previous level's Id
            INNER JOIN
        OrgChart oc ON e.ManagerId = oc.Id
)
-- Step 5: Select everything from the generated hierarchical tree
SELECT
    Id,
    Name,
    ManagerId,
    HierarchyLevel
FROM
    OrgChart
ORDER BY
    HierarchyLevel, Name;


/*
PROBLEM: Calculate a rolling 30-day sum of transaction amounts for an account.
WARNING: There may be days with NO transactions (missing rows).
Ensure the 30-day window is strictly calendar days, not just the last 30 rows.

TABLE: AccountTransactions (AccountId, TxnDate, Amount)
*/

-- Step 1: Select base columns
SELECT
    AccountId,
    TxnDate,
    Amount,

    -- Step 2: Use SUM() as a window function
    SUM(Amount) OVER (
        PARTITION BY AccountId
        ORDER BY TxnDate
        -- Step 3: Use RANGE instead of ROWS.
        -- RANGE evaluates the actual value of TxnDate.
        -- INTERVAL '30' DAY PRECEDING means "Look at all rows where the TxnDate
        -- is between (Current Date - 30 days) and Current Date".
        RANGE BETWEEN INTERVAL '30' DAY PRECEDING AND CURRENT ROW
    ) AS Rolling30DaySum

FROM
    AccountTransactions
ORDER BY
    AccountId, TxnDate;


/*
PROBLEM: A table tracks the history of interest rates for different account types.
When a rate changes, a new row is added with a new EffectiveStartDate.
Write a query to find the active interest rate for account type 'SAVINGS' exactly on '2025-06-15'.

TABLE: InterestRates (RateId, AccountType, InterestRate, EffectiveStartDate)
-- Note: There is NO 'EffectiveEndDate' column.
*/

-- Step 1: Create a CTE to generate the missing 'End Date' for each rate period
WITH RateHistory AS (
    SELECT
        AccountType,
        InterestRate,
        EffectiveStartDate,
        -- Step 2: Use LEAD() to find the Start Date of the NEXT rate change.
        -- If there is no next change (it's the current active rate), LEAD returns NULL.
        -- We use COALESCE to replace that NULL with a far-future date (e.g., 9999-12-31).
        COALESCE(
                LEAD(EffectiveStartDate) OVER (PARTITION BY AccountType ORDER BY EffectiveStartDate),
                '9999-12-31'
        ) AS EffectiveEndDate
    FROM
        InterestRates
)
-- Step 3: Query our enriched history table
SELECT
    AccountType,
    InterestRate,
    EffectiveStartDate
FROM
    RateHistory
-- Step 4: Perform the Point-in-Time filter
WHERE
    AccountType = 'SAVINGS'
  -- The target date must be >= the Start Date
  AND '2025-06-15' >= EffectiveStartDate
  -- The target date must be strictly < the End Date
  AND '2025-06-15' < EffectiveEndDate;
