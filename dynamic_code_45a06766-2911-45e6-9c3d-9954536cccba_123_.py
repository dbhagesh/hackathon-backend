def execute(numbers):
    total = 0
    for number in numbers:
        total += number
    return total
print(execute([1, 2, 3, 4, 5]))