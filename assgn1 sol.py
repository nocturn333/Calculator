# This program can be run as python2 or as python3
from __future__ import print_function

# tokens:
EOF = 0   #    end of input
VAR = 1   #    \$[a-zA-Z]*
EQ = 2    #    [=]
AND = 3   #    and
OR = 4    #    or
NOT = 5   #    not
FALSE = 6 #    False
TRUE = 7  #    True
LP = 8    #    \(
RP = 9    #    \)
ERR = 10  #    error

# Question 8 - Grammar
#
# stmt          : VAR maybeassign | expr
# maybeassign   : = expr | exprtail
# expr          : term exprtail
# exprtail      : AND term exprtail | OR term exprtail | epsilon
# term          : NOT factor | factor
# factor        : TRUE | FALSE | VAR anotherassign | LP stmt RP
# anotherassign : = expr | epsilon

import sys
debugParser = True
debugLexer = False

def show(indent,name,s,spp):
    if debugParser:
        print(indent+name+'("',end='');
        j = len(s)
        for i in range(spp,j):
            print(s[i],sep="",end="")
        print('")',end='\n')
        return
    else:
        return
#end show



def EatWhiteSpace(s,spp):
    j = len(s)
    if spp >= j:
        return spp

    while s[spp] == ' ' or s[spp] == '\n':
        spp=spp+1
        if spp >= j:
            break
    return spp
#end EatWhiteSpace


# function Parse ---------------------------------------------
def Parse(s,indent):
    show(indent,'Parse',s,0)
    
    spp = 0;
    indent1 = indent+' '
    res,spp = stmt(s,spp,indent1)
    if debugParser:
        print(indent+"back to Parse from prog")
    if not res:
        return False
    
    token = LookAhead(s,spp)
    if token != EOF:
        return False
    else:
        return True
#end Parse


# stmt : VAR maybeassign | expr
# function stmt --------------------------------------------- 
def stmt(s,spp,indent):
    show(indent,'stmt',s,spp)
    indent1 = indent+' '
    spp1 = spp #save position

    token = LookAhead(s,spp)
    if token == VAR:
        token,spp = NextToken(s,spp)
        res,spp = maybeassign(s,spp,indent1)
        if debugParser:
            print(indent+"back to stmt from maybeassign")
        if res:
            return True,spp
        else:
            return False,spp1
    else:
        res,spp = expr(s,spp,indent1)
        if debugParser:
            print(indent+"back to stmt from expr")
        if res:
            return True,spp
        else:
            return False,spp1
#end stmt



# maybeassign : = expr | exprtail
# function maybeassign --------------------------------------------- 
def maybeassign(s,spp,indent):
    show(indent,'maybeassign',s,spp)
    indent1 = indent+' '
    spp1 = spp # save position

    token = LookAhead(s,spp)
    if token == EQ:
        token,spp = NextToken(s,spp)
        res,spp = expr(s,spp,indent1)
        if debugParser:
            print(indent+"back to maybeassign from expr")
        if res:
            return True,spp
        else:
            return False,spp1
    else:
        res,spp = exprtail(s,spp,indent1)
        if debugParser:
            print(indent+"back to maybeassign from exprtail")
        if res:
            return True,spp
        else:
            return False,spp1
#end maybeassign

# expr : term exprtail
# function expr --------------------------------------------- 
def expr(s,spp,indent):
    show(indent,'expr',s,spp)
    indent1 = indent+' '
    spp1 = spp # save position

    res,spp = term(s,spp,indent1)
    if debugParser:
        print(indent+"back to expr from term")
    if not res:
        return False,spp1
    res,spp = exprtail(s,spp,indent1)
    if debugParser:
        print(indent+"back to expr from exprtail")
    if res:
        return True,spp
    else:
        return False,spp1
#end expr

# exprtail : AND term exprtail | OR term exprtail
# function exprtail --------------------------------------------- 
def exprtail(s,spp,indent):
    show(indent,'exprtail',s,spp)
    indent1 = indent+' '
    spp1 = spp # save position

    token = LookAhead(s,spp)
    if token == AND or token == OR:
        token,spp = NextToken(s,spp)
        res,spp = term(s,spp,indent1)
        if debugParser:
            print(indent+"back to exprtail from term")
        if res:
            return True,spp
        else:
            return False,spp1
        res,spp = exprtail(s,spp,indent1)
        if debugParser:
            print(indent+"back to exprtail from exprtail")
        if res:
            return True,spp
        else:
            return False,spp1
    else:
        return True,spp #epsilon
#end exprtail

# term : NOT factor | factor
# function term --------------------------------------------- 
def term(s,spp,indent):
    show(indent,'term',s,spp)
    indent1 = indent+' '
    spp1 = spp # save position

    token = LookAhead(s,spp)
    if token == NOT:
        token,spp = NextToken(s,spp)
    # in both cases, do factor next
    res,spp = factor(s,spp,indent1)
    if debugParser:
        print(indent+"back to term from factor")
    if res:
        return True,spp
    else:
        return False,spp1
#end expr

# factor : TRUE | FALSE | VAR anotherassign
# function factor --------------------------------------------- 
def factor(s,spp,indent):
    show(indent,'factor',s,spp)
    indent1 = indent+' '
    spp1 = spp # save position

    token = LookAhead(s,spp)
    if token == TRUE or token == FALSE:
        token,spp = NextToken(s,spp)
        return True,spp
    elif token == VAR:
        token,spp = NextToken(s,spp)
        res,spp = anotherassign(s,spp,indent1)
        if debugParser:
            print(indent+"back to factor from anotherassign")
        if res:
            return True,spp
        else:
            return False,spp1
    elif token == LP:
        token,spp = NextToken(s,spp)
        res,spp = stmt(s,spp,indent1)
        if debugParser:
            print(indent+"back to factor from stmt")
        if not res:
            return False,spp1
        token = LookAhead(s,spp)
        if token == EOF:
            return False,spp1
        token,spp = NextToken(s,spp)
        if token != RP:
            return False,spp1
        return True,spp
    else:
        return False,spp1 # error
#end factor


# anotherassign : = expr | epsilon
# function tail5 --------------------------------------------- 
def anotherassign(s,spp,indent):
    show(indent,'anotherassign',s,spp)
    indent1 = indent+' '
    spp1 = spp # save position

    token = LookAhead(s,spp)
    if token == EQ:
        token,spp = NextToken(s,spp)
        res,spp = expr(s,spp,indent1)
        if debugParser:
            print(indent+"back to anotherassign from expr")
        if res:
            return True,spp
        else:
            return False,spp1
    else:
        return True,spp #epsilon
#end anotherassign



# the scanner ####################################################

def letter(x):
    if ord(x)>=ord('a') and ord(x)<=ord('z'):
        return True
    if ord(x)>=ord('A') and ord(x)<=ord('Z'):
        return True
    return False

# function LookAhead ------------------------------------------- 
def LookAhead(s,spp):
    spp = EatWhiteSpace(s,spp)
    j = len(s);
    if spp >= j:
        return EOF
    
    if s[spp] == '=':
        if debugLexer:
            print("LookAhead: =")
        return EQ
    elif s[spp] == "(":
        if debugLexer:
            print("LookAhead: (")
        return LP
    elif s[spp] == ")":
        if debugLexer:
            print("LookAhead: )")
        return RP
    elif s[spp:spp+3] == "and":
        if debugLexer:
            print("LookAhead: and")
        return AND
    elif s[spp:spp+2] == "or":
        if debugLexer:
            print("LookAhead: or")
        return OR
    elif s[spp:spp+3] == "not":
        if debugLexer:
            print("LookAhead: not")
        return NOT
    elif s[spp:spp+5] == "False":
        if debugLexer:
            print("LookAhead: False")
        return FALSE
    elif s[spp:spp+4] == "True":
        if debugLexer:
            print("LookAhead: True")
        return TRUE
    elif s[spp] == "$":
        if debugLexer:
            print("LookAhead: VAR")
        return VAR
    else:
        if debugLexer:
            print("LookAhead: ERR")
        return ERR
#end LookAhead


# function NextToken --------------------------------------------- 
def NextToken(s,spp):
    spp1 = spp
    spp = EatWhiteSpace(s,spp)
    j = len(s);
    if spp >= j:
        return EOF
    
    if s[spp] == '=':
        if debugLexer:
            print("NextToken: =",spp+1)
        return EQ,spp+1
    elif s[spp] == "(":
        if debugLexer:
            print("NextToken: (",spp+1)
        return LP,spp+1
    elif s[spp] == ")":
        if debugLexer:
            print("NextToken: )",spp+1)
        return RP,spp+1
    elif s[spp:spp+3] == "and":
        if debugLexer:
            print("NextToken: and",spp+3)
        return AND,spp+3
    elif s[spp:spp+2] == "or":
        if debugLexer:
            print("NextToken: or",spp+2)
        return OR,spp+2
    elif s[spp:spp+3] == "not":
        if debugLexer:
            print("NextToken: not",spp+3)
        return NOT,spp+3
    elif s[spp:spp+5] == "False":
        if debugLexer:
            print("NextToken: False",spp+5)
        return FALSE,spp+5
    elif s[spp:spp+4] == "True":
        if debugLexer:
            print("NextToken: True",spp+4)
        return TRUE,spp+4
    elif s[spp] == "$":
        spp = spp+1
        while letter(s[spp]):
            spp = spp+1
            if spp >= j:
                break
        if debugLexer:
            print("NextToken: VAR",spp)
        return VAR,spp
    else:
        if debugLexer:
            print("NextToken: ERR",spp1)
        return ERR,spp1
#end NextToken


#main section
s = "$C = ($Beb = ($A and True)) or not False"
show('','main',s,0)
indent = ' '
res = Parse(s,indent);
if res:
    print("parsing OK")
else:
    print("parse Error")
#end main section

