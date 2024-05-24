female(usha).
female(kiran).
female(asha).
female(pranjal).
male(krishna).
male(jay).
male(piyush).
parent(usha,jay).
parent(krishna,jay).
parent(krishna,asha).
parent(jay, piyush).
parent(jay,pranjal).
mother(X,Y) :- parent(X,Y), female(X).
father(X,Y) :- parent(X,Y), male(X).
has_child(X) :- parent(X,_).

# consult('D://Computers/AILab.pl')
# female(usha). -- True
# female(Jay). -- Jay = usha
# parent(jay,kiran) - false


