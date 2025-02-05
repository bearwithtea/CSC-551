## Question 1
>Which of the following are valid identifiers? For each valid identifier, provide a parse tree (with the `id` abstraction at the root). If invalid, briefly describe what aspect violates the rules.

`Q` is valid, shown by the parse tree below:
![[Pasted image 20250128090752.png | Parse tree for `Q` | 300]]

`4b` is invalid, since it starts with a number.

`a1BC` is valid, shown by the parse tree below:
![[Pasted image 20250128090846.png | Parse tree for `a1BC` | 400]]

`X_1` is invalid, since `_` is not a letter or digit.
## Question 2
>Which of the following are valid numbers? For each valid number, provide a parse tree (with the number abstraction at the root). If invalid, briefly describe what aspect violates the rules.

`44` is a valid number, shown by the parse tree below:
![[Pasted image 20250123153808.png | Parse tree for `44` | 300]]

`-44` is a valid number, shown by the parse tree below:
![[Pasted image 20250122164950.png | Parse tree for `44` | 300]]

`0.5` is a valid number, shown by the parse tree below:
![[Pasted image 20250128091447.png | Parse tree for `0.5` | 400]]

`.5` is an invalid number, since there needs to be a number preceding the decimal point.

`5.` is valid, shown by the parse tree below:
![[Pasted image 20250128091516.png | Parse tree for `5.` | 400]]

`-.2` is invalid, since there needs to be a number preceding the decimal point.
`--9` is invalid, since the preceding `-` is not repetitive.
`E4` is invalid, since there needs to be a number preceding the `E`.

`-3e2` is valid, shown by the parse tree below:
![[Pasted image 20250128091700.png | Parse tree for `-3e2` | 400]]

`-3.1e2.5` is invalid, since numbers after `e/E` need to be whole.
## Question 3
>Which of the following are valid expressions? For each valid expression, provide a parse tree (with the `expr` abstraction at the root). If invalid, briefly describe what aspect violates the rules.

`x` is valid, shown by the parse tree below: %%said was fine%%
![[Pasted image 20250123161359.png | Parse tree for `x` | 150]]

`(x)` is valid, shown by the parse tree below:
![[Pasted image 20250123161102.png | Parse tree for `(x)` | 300]]

`(-x)`  is invalid, because `-X` is not a valid `<id>` or `<op>`.

`(x + 1)` is invalid, because `+` is not a valid expression.

`(+ x 1)` is valid, shown by the parse tree below: 
![[Pasted image 20250128141400.png | Parse tree for `(+ x 1)` | 400]]

`(- x 1)` is invalid, since `-` is not an operator
## Question 4
>Which of the following are valid if statements? If valid, provide a parse tree (with the if abstraction at the root). If invalid, briefly describe what aspect violates the rules.

```python
if (== x y)
{
	print "eq"
}
```
is invalid since each `if` statement must have an `else`.

```python
if true {}
else {}
```
is valid, shown by the following parse tree:
![[Pasted image 20250128141734.png | 400]]

```python
if (> m n)
{
	print 0
}
else
{
	print 1
}
```
is valid, shown by the parse tree below:
![[Pasted image 20250128142334.png | 600]]

```python
if (avg >= 80)
{
	if (avg >= 90)
		print "A"Å
	else
		print "B"
}
else
{
	print "F"
}
```
is invalid because there must be `{}` after the `if` statement
## Question 5
>Is it valid to mix the types of values in a list, e.g., ["foo" 3]? If so provide a parse tree for this list expression. If not, explain why not.

Mixing the types of a list is valid, shown by the the parse tree below:
![[Pasted image 20250129124812.png | Parse tree for mixed list | 500]]
## Question 6
>Is it valid to nest a list inside another list, e.g., [1 [2 3]]? If so provide a parse tree for this list expression. If not, explain why not.

Nesting a list within a list is valid, shown by the parse tree below:
![[Pasted image 20250128143125.png | Parse tree for nested list | 600]]
## Question 7
> Give an example of an assignment statement (`assign`) that is as short as possible, in terms of number of tokens.
```python
a = 0
```
## Question 8
>Give an example of a while statement (`while`) that is as short as possible, in terms of number of tokens. 
```python
while 0{}
```
## Question 9
> Give an example of a function declaration (`func`) that is as short as possible, in terms of number of tokens. 
```python
func a(){}
```