Design Pattern:


 - Builder Pattern:   
used in Todo class and DisplayOption class to help construct Todo and DisplayOption object. A Todo only has one required field text. Builder pattern can help to build with optional fields and avoid writing a bunch of constructors. DisplayOption has some default fields and they could be updated according to user inputs, so using a builder can simplify code and make constructing more easily.
 - Visitor Pattern:   
used in PriorityComparator class and DueDateComparator class. Visitor pattern can perform new operations on a group of objects without modifying the structures of the object. With the help of a visitor pattern, we can move the operational logic from the object to another class. In this project, we have these two comparators to help to sort the Todo list under different conditions, without modifying the class Todo.
 - Iterator Pattern:      
used in TodoIterator class. Iterator pattern provides a way to traverse through a group of projects based on different requirements. In this project, since we need to filter the todo list under different conditions, we implement TodoIterator class to handle the filter work.