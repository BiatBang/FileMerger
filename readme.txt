Overview
--------
Write a Java program that reads input files containing tabular data, merges the data and outputs a CSV file 
containing the combined data.

Use the given RecordMerger class as a starting point.
We have provided 2 sample files: "first.html" and "second.csv" in the "data" directory.

1. Html Files: Will contain a table called "directory". Read and extract the table.

2. Csv Files: Standard csv format. Read and extract the table.

3. Combine and merge the input tables into one, by consolidating duplicated columns and output the results in a file named "combined.csv".
   - For the merge operation, assume "ID" is unique.
   - The "ID" column in the output file "combined.csv" should be sorted in ascending order.


Expected Output
----------------
- Your program outputs a file called "combined.csv"
- The output file must contain no duplicate IDs.
- The rows must be sorted by ID in ascending order.
- A header row is required in the output file.


Requirements
------------
1. Your program must allow:
    a. any number of input files (html or csv)
    b. any number of columns in the csv or html files.
    c. the data can be in any language (Chinese, French, English, etc).

2. Your program must be easily extended to support additional file types (in the future).

3. Update the included run.sh file to run your program, or include additional README file on how to run it. 

4. Your program must be coded with Java best practices. Hint: Object Oriented Programming, Design Patterns, Tests etc


Feel free to ask questions by email and be sure to document any assumptions you have made in your program.

Note: you can include any open-source libraries you need in addition to what's provided in "lib" directory, just update the build config.