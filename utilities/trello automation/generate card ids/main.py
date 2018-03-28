import sys
from trello import TrelloClient

card_name_prefix = "GZ-"

def rename_cards_for_list(a_list):
    print("Renaming cards for " + a_list.name + "...")

    for card in a_list.list_cards():
        if card.name.startswith(card_name_prefix):
            continue

        card_short_id = str(card.short_id).zfill(4)
        card_prefix = card_name_prefix + card_short_id
        print(card_prefix + " " + card.name)
        card.set_name(card_prefix + " " + card.name)

    print("Done with " + a_list.name)

client = TrelloClient(
    api_key='<your-key>',
    api_secret='<your-secret>',
    token='<your-oauth-token-key>',
    token_secret='<your-oauth-token-secret>'
)

print("Fethcing boards list...")
all_boards = client.list_boards()

count = 0
for board in all_boards:
    print(str(count) + " - " + board.name)
    count+=1

selected_board_id = int(input("Select board id: "))
if 0 > selected_board_id >= len(all_boards):
    print("Invalid selected board id. QUITTING...")
    sys.exit(1)

current_board = all_boards[selected_board_id]
print("so, you selected: " + current_board.name)
all_lists = current_board.list_lists()

count = 0
print("-1 - All lists")
for a_list in all_lists:
    print(str(count) + " - " + a_list.name)
    count+=1

selected_list_id = int(input("Select list id: "))
if -1 > selected_list_id >= len(all_lists):
    print("Invalid selected list id. QUITTING...")
    sys.exit(1)

if selected_list_id > -1:
    rename_cards_for_list(current_board.get_list(all_lists[selected_list_id].id))
else:
    for a_list in all_lists:
        rename_cards_for_list(a_list)

print("Work done...")





