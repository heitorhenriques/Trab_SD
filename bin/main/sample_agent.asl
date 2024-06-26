!get_number.

+!get_number: true
<-  .my_name(M);
    .term2string(M,N);
    .nth(8,N,X);
    +my_number(X).

+init: my_number(X) & .term2string(A,X) & .all_names(B) & .length(B,N) & A == N
<-  setTableMessage(A,"Eu sou o maior");
    .wait(2000);
    setTableMessage(A,"Mandando mensagem para todos");
    .wait(2000);
    .broadcast(achieve,maior).

+killed: killed(V) & my_number(N) & .term2string(V,N)
<-  setTableMessage(V,"Eu fui morto").

+perceived: perceived(V) & my_number(N) & .term2string(V,N) & killed(A) & lider(B) & .term2string(A,B)
<-  setTableMessage(V,"Percebi que o lider está morto");
    .wait(2000);
    if (V+1 < 7){
        .concat("Mandando mensagem para o processo ",V+1,M);
        setTableMessage(V,M);
        .concat("processo",V+1,X);
        .term2string(Y,X);
        .wait(2000);
        .send(Y,achieve,responder);
    }
    .

+!responder[source(S)]: killed(V) & my_number(N) & .term2string(V,N)
<- .send(S,achieve,timeout).

+!responder[source(S)]: my_number(N)
<-  .term2string(V,N);
    .concat("Recebi uma mensagem de ", S,M);
    setTableMessage(V,M);
    .wait(2000);
    .send(S,achieve,resposta);
    if (V+1 < 7){
        .concat("Mandando mensagem para o processo ",V+1,Z);
        setTableMessage(V,Z);
        .concat("processo",V+1,X);
        .term2string(Y,X);
        .wait(2000);
        .send(Y,achieve,responder);
    }
    .

+!resposta: my_number(N)
<-  .term2string(V,N);
    setTableMessage(V,"O processo maior respondeu").

+!maior[source(S)]: killed(V) & my_number(N) & not(.term2string(V,N)) & .term2string(S,X) & .nth(8,X,Y) & .term2string(Z,Y) & .term2string(A,N) & Z < A
<-  .concat("Eu sou maior que o ", S,M);
    setTableMessage(A,M);
    .wait(2000);
    setTableMessage(A,"Mandando mensagem para todos");
    .wait(2000);
    .broadcast(achieve,maior).

+!maior[source(S)]: killed(V) & my_number(N) & not(.term2string(V,N)) & lider(B)
<-  .term2string(X,N);
    .concat("O ",S," é o lider",M);
    .term2string(S,A);
    .nth(8,A,Y);
    -lider(B);
    +lider(Y);
    setTableMessage(X,M).

+!maior[source(S)]: killed(V) & my_number(N) & not(.term2string(V,N))
<-  .term2string(X,N);
    .concat("O ",S," é o lider",M);
    .term2string(S,A);
    .nth(8,A,Y);
    +lider(Y);
    setTableMessage(X,M).


+!timeout: my_number(N)
<-  .term2string(V,N);
    setTableMessage(V,"O processo maior não me respondeu");
    .wait(2000);
    setTableMessage(V,"Mandando mensagem para todos");
    .wait(2000);
    .broadcast(achieve,maior).

+revive: killed(X) & my_number(N) & .term2string(X,N)
<-  .term2string(V,N);
    setTableMessage(V,"Fui revivido");
    .wait(2000);
    setTableMessage(V,"Mandando mensagem para todos");
    .broadcast(achieve,maior).

+closed: true
<- .my_name(Me);
.kill_agent(Me).


{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }