
wall(cell(I, J)) :-
    I < 0; I > 2; J < 0; J > 2.

teleportal(cell(2, 2)).
obstacle(cell(10, 10)).
%obstacle(cell(0, 1)).
%obstacle(cell(0, 2)).

rock_location(cell(1, 1), s0).
pressure_pad(cell(0, 2)).

at_location(cell(0, 0), s0).


oposite(up, down).
oposite(down, up).
oposite(left, right).
oposite(right, left).

adjacent(cell(I1, J1), cell(I2, J1), up) :- I2 is I1 + 1.
adjacent(cell(I1, J1), cell(I2, J1), down) :- I2 is I1 - 1.
adjacent(cell(I1, J1), cell(I1, J2), left) :- J2 is J1 + 1.
adjacent(cell(I1, J1), cell(I1, J2), right) :- J2 is J1 - 1.

% teleportal(X), call_with_depth_limit(at_location(X, Y), 10, R).
valid_cell(Cell) :- % TODO:: add teleportal if the rocks are not on the pads,
  \+ wall(Cell), \+ obstacle(Cell).

at_location(Curr_cell, result(Action, State)) :-
  teleportal(Curr_cell), pressure_pad(Pad), rock_location(Pad, State), adjacent(Curr_cell, New_cell,
   Direction), Action = move(Direction), valid_cell(Curr_cell), valid_cell(New_cell), at_location(New_cell, State),
  \+ rock_location(Curr_cell, State).

at_location(Curr_cell, result(Action, State)) :-
  \+ teleportal(Curr_cell), adjacent(Curr_cell, New_cell, Direction), Action = move(Direction), valid_cell(Curr_cell),
   valid_cell(New_cell), at_location(New_cell, State),
  \+ rock_location(Curr_cell, State).

at_location(Curr_cell, result(Action, State)) :-
  \+ teleportal(Curr_cell), adjacent(Curr_cell, Agent_cell, Direction), at_location(Agent_cell, State),
  rock_location(Curr_cell, State), oposite(Direction, Oposite_direction), Action = push(Direction), valid_cell(Curr_cell),
  adjacent(Curr_cell, Rock_cell, Oposite_direction), valid_cell(Rock_cell).

% at_location(Curr_cell, result(Action, State)) :-
%   adjacent(Curr_cell, New_cell, Direction), Action = move(Direction), valid_cell(Curr_cell), valid_cell(New_cell), at_location(New_cell, State).

rock_location(Curr_cell, result(Action, State)) :-
  adjacent(Curr_cell, New_cell, Direction), adjacent(New_cell, Agent_cell, Direction), Action = push(Direction),
  rock_location(New_cell, State), \+ rock_location(Curr_cell, State), at_location(Agent_cell, State), valid_cell(Curr_cell).

rock_location(Curr_cell, result(Action, State)) :-
  rock_location(Curr_cell, State),
  \+ (adjacent(Curr_cell, Agent_cell, Direction), at_location(Agent_cell, State), Action = push(Direction), oposite(Direction, Oposite_direction),
  adjacent(Curr_cell, New_cell, Oposite_direction), \+ rock_location(New_cell, State), valid_cell(New_cell)).
