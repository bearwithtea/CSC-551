#!/bin/bash

# Check if the actual.txt file exists
if [ ! -f "actual.txt" ]; then
    echo "Error: actual.txt file not found!"
    exit 1
fi

# Check if the expected.txt file exists
if [ ! -f "expected.txt" ]; then
    echo "Error: expected.txt file not found!"
    exit 1
fi

# Compare the files
diff_output=$(diff -u actual.txt expected.txt)
exit_code=$?

if [ $exit_code -eq 0 ]; then
    echo "SUCCESS: The files are identical!"
    exit 0
else
    echo "FAILURE: The files are different!"
    echo "Here are the differences:"
    echo "$diff_output"
    exit 1
fi