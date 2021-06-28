package utility;

import exceptions.IncorrectValueException;
import exceptions.NullFieldException;
import exceptions.ValidationException;

public interface FieldCheckerHelp<T> {
    T check(String str) throws NullFieldException, IncorrectValueException;
}
