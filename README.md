# README
To build use:
`mvn package`

To run use:
`./run`

To start the server use:
`./run --gui [--port=<port>]`


What’s the best way to output this data?

We agreed that the best way to output this data is to write the results to a file. Once the results are generated, we'd want them to be permanent somewhere so we can go back and look at it for reference later on. (We did not have time to do this, though, so they just print in our REPL interface!)

the minority issue:

In the context of the interview that was linked in the handout, it's possible that female students are less likely to emphasize their positive traits, or to downplay their contributions. This could be a problem for our recommender algorithm, because it would cause a bias in the data, since students are required to provide positive and negative traits about themselves. This same concept could apply to other types of minorities as well. Because of this, students who belong in minority groups may not be paired up with group members that best match their traits.