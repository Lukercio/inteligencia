#PythonCode --- PyCharm 2016.3

from csv import reader
from random import random

#########################################
#  Created by ldlopes on 11/02/17.	#
#					#
########################################

import math

def readFile(startNet, f1, f2):
    dataset = list()

    with open(startNet, 'r') as file:
        rd = reader(file)
        for row in rd:
            if not row:
                continue
            if int(float(row[0].split(' ')[0])) == f1 or int(float(row[0].split(' ')[0])) == f2:
                dataset.append(row[0].split(' ')[1:-1]+[row[0].split(' ')[0]])

    for i in range(len(dataset[0])-1):
        colToFloat(dataset, i)

    colToInt(dataset, len(dataset[0])-1)
    return dataset

def transfer(a):
    return 1.0 / (1.0 + math.exp(-a))


def activated(p, input):
    a = p[-1]
    for i in range(len(p)-1):
        a += p[i] * input[i]
    return a

def fProp(net, row):
    entrada = row
    for camada in net:
        nova_entrada = []
        for n in camada:
            a = activated(n['p'], entrada)
            n['sd'] = transfer(a)
            nova_entrada.append(n['sd'])
        entrada = nova_entrada
    return entrada


def transferD(sd):
    return sd * (1.0 - sd)


def bpError(net, nxt):

    for i in reversed(range(len(net))):
        camada = net[i]
        errs = list()

        if i != len(net)-1:
            for j in range(len(camada)):
                err = 0.0
                for n in net[i + 1]:
                    err += (n['p'][j] * n['delta'])
                errs.append(err)
        else:
            for j in range(len(camada)):
                n = camada[j]
                errs.append(nxt[j] - n['sd'])
        
for j in range(len(camada)):
            n = camada[j]
            n['delta'] = errs[j] * transferD(n['sd'])

def backPropagation(train, teste, txTraining, nEp, hideNumber):
    predict = list()

    num_input = len(train[0]) - 1
    nSds = len(set([row[-1] for row in train]))

    net = startNet(num_input, hideNumber, nSds)
    trainner(net, train, txTraining, nEp, nSds)

    for row in teste:
        pred = predicao(net, row)
        predict.append(pred)
    return predict

def currentiza_p(net, row, learningMoveTax):
    for i in range(len(net)):
        input = row[:-1]
        if i != 0:
            input = [n['sd'] for n in net[i - 1]]
        for n in net[i]:
            for j in range(len(input)):
                n['p'][j] += learningMoveTax * n['delta'] * input[j]
        n['p'][-1] += learningMoveTax * n['delta']


def trainner(net, t, learningMoveTax, nEpc, nSds):
    for ep in range(nEpc):
        soma_err = 0

        for row in t:
            sds = fProp(net, row)
            nxt = [0 for i in range(nSds)]
            nxt[row[-1]] = 1
            soma_err += sum([(nxt[i]-sds[i])**2 for i in range(len(nxt))])
            bpError(net, nxt)
            currentiza_p(net, row, learningMoveTax)


def predicao(net, row):
    sds = fProp(net, row)
    return sds.index(max(sds))



def acc(current, predict):
    x= 0
    for i in range(len(current)):
        if current[i] == predict[i]:
            x+= 1
    return x/ float(len(current)) * 100.0

def colToInt(dataset, col):
    vClass = [row[col] for row in dataset]

    unique = set(vClass)
    lookup = dict()

    for i, valor in enumerate(unique):
        lookup[valor] = i
    for row in dataset:
        row[col] = lookup[row[col]]
    return lookup


def colToFloat(dataset, col):
    for row in dataset:
        row[col] = float(row[col].strip())



def startNet(num_input, hideNumber, nSds):
    net = list()
    camada_escondida = [{'p':[random() for i in range(num_input + 1)]} for i in range(hideNumber)]
    net.append(camada_escondida)
    camada_sd = [{'p':[random() for i in range(hideNumber + 1)]} for i in range(nSds)]
    net.append(camada_sd)
    return net

