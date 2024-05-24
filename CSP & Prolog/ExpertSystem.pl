% Facts about the animals
animal(lion).
animal(elephant).
animal(zebra).
animal(penguin).
animal(giraffe).
animal(crocodile).
animal(bear).
animal(rabbit).
animal(shark).
animal(turtle).
% Habitats
habitat(lion, savannah).
habitat(elephant, savannah).
habitat(zebra, savannah).
habitat(penguin, antarctica).
habitat(giraffe, savannah).
habitat(crocodile, wetlands).
habitat(bear, forest).
habitat(rabbit, meadow).
habitat(shark, ocean).
habitat(turtle, wetlands).
% Diets
diet(lion, carnivore).
diet(elephant, herbivore).
diet(zebra, herbivore).
diet(penguin, carnivore).
diet(giraffe, herbivore).
diet(crocodile, carnivore).
diet(bear, omnivore).
diet(rabbit, herbivore).
diet(shark, carnivore).
diet(turtle, herbivore).

% Rule to check if two animals share the same habitat
shares_habitat(Animal1, Animal2):-habitat(Animal1, Habitat),habitat(Animal2, Habitat),Animal1 \= Animal2.

% Rule to determine which animals a given animal could eat
preys_on(Predator, Prey) :-diet(Predator, carnivore),diet(Prey, herbivore).

% Rule to determine which animals can coexist peacefully
coexists_peacefully(Animal1, Animal2) :-habitat(Animal1, Habitat),habitat(Animal2, Habitat),Animal1 \= Animal2,\+ preys_on(Animal1, Animal2),\+ preys_on(Animal2, Animal1).

# shares_habitat (lion,bear)
# diet(penguin, carnivore) - true
# preys_on(lion,zebra) -true
