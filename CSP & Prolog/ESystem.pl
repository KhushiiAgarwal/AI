% Rule to classify sentiment as positive
sentiment(X, positive) :- positive(X).

% Rule to classify sentiment as negative
sentiment(X, negative) :- negative(X).

% Rule to classify sentiment as neutral
sentiment(X, neutral) :- neutral(X).

% Statement
positive(good).
positive(happy).
positive(lovely).
positive(nice).
positive(amazing).
negative(bad).
negative(sad).
negative(dissatisfied).
negative(wrong).
negative(worst).
neutral(ok).
neutral(fine).
neutral(middleground).

