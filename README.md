# Vimcash: A simple text based double entry accounting system.

*Vimcash* is a text-based accounting system using the [vimjournal](https://github.com/rogerkeays/vimjournal) file format. It uses a double-entry model, but provides a convenient shorthand for single-entry records:

    20200906_1830 |- sunnies: USD 2 clothes CASH
    20200907_2000 |- aima earphones x3: USD 4.5 hardware CASH
    20200907_2000 |- silicon swimming caps x2: USD 2 sport CASH
    20200908_1445 |- visa extension: USD 30 tax CASH
    20200909_1700 |- bus to the city: USD 6.25 travel CASH
    20200911_1010 |- laptop charge repair: USD 1.25 hardware CASH
    20200911_1355 |- khmer house bungalow: USD 14 accomm CASH

By convention, asset and liability accounts use capital letters (for the balance sheet report) and income and expense accounts are in lowercase (for the profit and loss statement).
 
Although the above records look like single entry, they all have two accounts, so are really double-entry. We just put them on one line. Sometimes a transaction involves more than two accounts (or more than one currency). In that case, you can list the splits one per line and reference the special *split account* **`-`**.

    20200912_1500 |= atm withdrawal: USD 390 CASH -
    20200912_1500 |- atm withdrawal: USD 5 fees -
    20200912_1500 |= atm withdrawal: AUD 544.62 - CITI

Records with the same timestamp using the `-` account are considered to part of the same transaction. Especially complicated transactions may require two split accounts, but these are rare. You can use `-1` and `-2`.

*Vimcash* allows you to use any currencies, but does not try to convert them for reports. Thus, your reports may include different currencies.

## File Format

The basic format is:

    YYYYMMDD_HHMM [type]| [description]: [currency] [amount] [debit account] [credit account] [tags]

The type may be one of the following:

    * investment
    + income
    = transfer
    - expense
    x divestment

The investment and divestment types are used to calculate returns of investment, but otherwise the types do not affect reports. The balance sheet and profit and loss reports are based on debit and credit accounts, so you still have to get them in the right order.

For a description of the tags, please see [vimjournal](https://github.com/rogerkeays/vimjournal). They do not have any significance in *vimcash*.

## Command Line Tools

*Vimcash* is still a work in progress. At the moment, there are just a few Kotlin functions that I run from the Kotlin shell.

## Related Resources

  * [Vimjournal](https://github.com/rogerkeays/vimjournal): a simple text format for organising large amounts of information.
  * [Vimliner](https://github.com/rogerkeays/vimliner): the simplest outliner for VIM.
  * [More stuff you never knew you wanted](https://rogerkeays.com).
 
