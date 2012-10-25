#include <iostream>
#include <list>

using std::string;
using std::cout;

/**
 * Ok people, as of today I've not really written much C++ for 3.5 years. I forgot
 * about the pointer logic, dereferencing pointers, object comparisons, lack of auto
 * garbage collection, etc etc etc
 *
 * But, after a little while (hour or so), was able to hack this together.
 *
 * Basic linked list implementation.
 *
 * @author Chad Ellis (ellistrator@yahoo.com)
 * @version 1.0-102512
 */
class Link
{
	// String value and a pointer to the next guy
	string value;
	Link* nextLink;

public:

	Link(const string& newValue)
	{
		value = newValue;
		this->nextLink = 0;
	}

	string getValue()
	{
		return this->value;
	}

	Link* next()
	{
		return this->nextLink;
	}

	void setNext(Link* value)
	{
		this->nextLink = value;
	}
};

/**
 * Nothing fancy here (no tail pointer for instance). We're left to traverse the list
 * whenever we need from the top. Provides add / remove / insert... I suppose I could do
 * an update, but that logic is a lesser variant of the insert method. Call me lazy
 */
class LinkedList
{
	Link* head;

public:
	LinkedList()
	{
		head = 0;
	}

	/**
	 * Oh noooooooooooooooooooooooo!!! Gone forever back to those billions of bytes of memory
	 */
	~LinkedList()
	{
		Link* it = head;
		Link* gonner = 0;
		while ( it != 0 )
		{
			gonner = it;
			it = it->next();
			delete gonner;
		}
	}

	/**
	 * Start at the top and go to the end!
	 */
	void add(Link* link)
	{
		if ( this->head == 0 )
			head = link;
		else
		{
			Link* it = head;
			while ( it->next() != 0 )
			{
				if ( it->next() == link )
					return;

				it = it->next();
			}

			it->setNext(link);
		}
	}

	/**
	 * Don't be afraid to delete if you know what you're doing.
	 */
	void remove(const string& value)
	{
		Link* it = head;
		Link* prev = it;

		while ( it != 0 )
		{
			if ( it->getValue() == value )
			{
				if ( it == head )
				{
					// it is the new head (may already be)
					Link* gonner = head;
					head = head->next();
					delete gonner;
				}
				else
				{
					prev->setNext(it->next());
					delete it;
				}

				it = 0;
			}
			else
			{
				// Iterate, keeping prev one step behind
				if ( prev != it )
					prev = it;
				it = it->next();
			}
		}
	}

	/**
	 * Very very much like remove. Shoot I shoulda just copied and pasted!
	 */
	void insert(Link* link, const string& before)
	{
		Link* it = head;
		Link* prev = it;

		while ( it != 0 )
		{
			if ( it->getValue() == before )
			{
				if ( it == head )
				{
					link->setNext(head);
					head = link;
				}
				else
				{
					prev->setNext(link);
					link->setNext(it);
				}
				it = 0;
			}
			else
			{
				// Iterate, keeping prev one step behind
				if ( prev != it )
					prev = it;
				it = it->next();
			}
		}
	}

	/**
	 * A better name for dump might be output, or toString... nah
	 */
	void dump()
	{
		Link* it = head;
		while ( it != 0 )
		{
			cout << "[" << it->getValue() << "]";
			it = it->next();
		}
		cout << "\n";
	}
};

int main()
{
	/*
	 * Exercise!!! Check the boundaries.
	 */
	Link* myLink = new Link("foo");
	cout << myLink->getValue() << "\n";

	LinkedList* myList = new LinkedList();
	myList->add(myLink);
	myList->add(new Link("bar"));
	myList->add(new Link("scooby"));
	myList->add(new Link("doo"));

	myList->dump();	// foo bar scooby doo

	myList->remove("foo");
	myList->dump(); // bar scooby doo

	myList->remove("doo");
	myList->dump(); // bar scooby

	myList->remove("bar");
	myList->dump(); // scooby

	myList->remove("foo");
	myList->dump(); // scooby

	myList->insert(new Link("newfoo"), "scooby");
	myList->dump(); // newfoo scooby

	myList->insert(new Link("newbar"), "scooby");
	myList->dump(); // newfoo newbar scooby

	// This one creates a small leak as "bogus" is not found
	// so the "new Link("bogus")" is not recovered. The insert method
	// should not assume it needs deleting.
	myList->insert(new Link("bogus"), "bogus");
	myList->dump(); // newfoo newbar scooby

	delete myList;
}
