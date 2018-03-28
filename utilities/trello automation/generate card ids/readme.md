# Automatically generate trello card name prefixed with id's.

## 1-time setup 

#### Make sure you have 'virtualenv'

```
pip install virtualenv
```

#### Create Virtual Environment
```
virtualenv generate-card-ids
```

#### Activate Virtual Environment
```
generate-card-ids\Scripts\activate
```

#### Install prerequisites
```
pip install -r requirements.txt
```

#### Get Access tokens

You can get your [Api key and Api Secret here](https://trello.com/app-key)

Follow instructions under [Getting your Trello OAuth Token](https://github.com/sarumont/py-trello#getting-your-trello-oauth-token) to get tokens.

#### Replace your tokens

In main.py place your values here:
```
client = TrelloClient(
    api_key='<your-api-key>',
    api_secret='<your-api-secret>',
    token='<your-oauth-token-key>',
    token_secret='<your-oauth-token-secret>'
)
```

## Running

```
generate-card-ids\Scripts\activate
python main.py
```
- Follow instructions in console.
- ???
- Profit
