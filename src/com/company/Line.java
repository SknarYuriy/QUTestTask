package com.company;

public class Line {
    private double serviceId;
    private double questionTypeId;
    private char responseType;
    private char character;

    public double getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(double serviceId)
    {
        this.serviceId = serviceId;
    }

    public double getQuestionTypeId()
    {
        return questionTypeId;
    }

    public void setQuestionTypeId(double questionTypeId)
    {
        this.questionTypeId = questionTypeId;
    }

    public char getResponseType()
    {
        return responseType;
    }

    public void setResponseType(char responseType)
    {
        this.responseType = responseType;
    }

    public char getCharacter()
    {
        return character;
    }

    public void setCharacter(char character)
    {
        this.character = character;
    }
}
