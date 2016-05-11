Alright, quick [Leiningen](http://leiningen.org/) introduction just in case you don't know what it is.

Leiningen (or lein) is the go-to project management tool for Clojure nerds.

You're going to need to install it to run my project but it's really simple and since you sent me a .tar for the puzzle I'm going to assume you're on Linux (I'll also link to the windows installer just in case).

In the resources folder, you will see [lein.sh](https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein). Put that somewhere on your path (~/bin or where ever you want). After that chmod it so you can execute it. And then run the command `lein`. You should get a printout of a bunch of commands. If you get that, try running `lein repl`, this will open up a clojure REPL in your terminal. If that work, then we are definitely set.

For Windows users [go here and get use the installer](http://leiningen-win-installer.djpowell.net/)!

For Ubuntu users, don't, I repeat, do not just run `sudo apt-get install lein`. That version of leiningen is out-dated and that repository is no longer maintained. I don't think it would actually be a problem, but we're on version 2.x.x and apt is stuck at 1.7.x.

Okay, lein is installed. Now what!?

Open up a terminal, cd into the root project folder (/vistar-app/) and execute `lein run`. That will start a jetty server for you at localhost:8080.

Next, open up another terminal (I know, it's a pain, but it's the Clojure life). Here's where things get iffy...

curl -d "?latitude=-77.23452&longitude=40.541435" http://localhost:8080 should print out the state. However, I recently blew up my linux distro on this computer and for some reason, I can't install curl anymore. So I wasn't able to actually test this with curl. I did test it with wget and wrote it to an html file and it worked. You can also just navigate to http://localhost:8080/?latitude=_____&longitude=______ and see the results.

Again, if things aren't working, give me a call and we can troubleshoot them!

As for the actual code, the server stuff is in /vistar-app/src/vistar_app/core.clj and the logic stuff is in /vistar-app/src/vistar_app/logic.clj. The math behind it is the standard ray crossing method of checking containment (thank you topology). Clojure is a bit weird to read but I think along with the comments it won't be bad, even for a non-Lisp-er.

So yeah, that's the guts of it. I'm not going to lie and say it was easy or that it took me an hour. It took me all in all about 4 hours to build this whole thing. But, I did enjoy it, this is definitely the most interesting and challenging interview question I've ever had the opportunity to solve!
