Feature: Generating summary for current season starting with seasonal shows from MyAnimeList

  Scenario: Finding out which shows each voice actor (seiyuu in Japanese) is involved with in the given season works as follows:

    - Using the seasonal anime port, fetch the full list of anime that belong to the "current" season

    - Using the anime characters port, for each of these seasonal anime fetch the list of characters belonging to these shows

    - Using the above information, we are able to narrow down on the IDs of specific seiyuus who have an involvement in the current season

    - Using this set of specific seiyuus, fetch the full details of these seiyuus from the seiyuu port (eg. name, full set of voice acting roles etc)

    - When producing the final summary, each seiyuu's roles in the current season is a subset of their overall roles, and is determined by filtering
      against the IDs of anime that belong to the current season (from step 1)

    - This gives us the desired summary of seiyuus and their roles in the current season, and also a reference for the all time roles that they have voiced

    Given the following seasonal anime fetched from the seasonal anime port
      | id | title             | url  |
      | 1  | Kantai Collection | URL1 |
      | 2  | Fate Zero         | URL2 |
      | 3  | Oregairu          | URL3 |

    And the following characters returned from the anime characters port
      | anime id | char id | name              | role | url   | image url |
      | 1        | 1       | Kongou            | Main | URL5  | URL6      |
      | 2        | 2       | Emiya Kiritsugu   | Main | URL7  | URL8      |
      | 3        | 3       | Hikigaya Hachiman | Main | URL9  | URL10     |
      | 3        | 4       | Yuigahama Yui     | Main | URL11 | URL12     |

    And the following seiyuu IDs for each character returned from the anime characters port
      | char id | seiyuu id |
      | 1       | 1         |
      | 2       | 2         |
      | 3       | 3         |
      | 4       | 1         |

    And the following seiyuu details returned from the seiyuu port
      | seiyuu id | url   | image url | name          |
      | 1         | URL13 | URL14     | Touyama Nao   |
      | 2         | URL15 | URL16     | Rikiya Koyama |
      | 3         | URL17 | URL18     | Takuya Eguchi |

    And the following seiyuu roles returned from the seiyuu port
      | seiyuu id | anime name        | anime url | anime id | character name    | character url | character image url |
      | 1         | Kantai Collection | URL1      | 1        | Kongou            | URL5          | URL6                |
      | 1         | Oregairu          | URL3      | 3        | Yuigahama Yui     | URL11         | URL12               |
      | 1         | Yuru Camp         | URL19     | 4        | Shima Rin         | URL20         | URL21               |
      | 1         | Nisekoi           | URL22     | 5        | Kirisaki Chitoge  | URL23         | URL24               |
      | 2         | Fate Zero         | URL2      | 2        | Emiya Kiritsugu   | URL7          | URL8                |
      | 3         | Oregairu          | URL3      | 3        | Hikigaya Hachiman | URL9          | URL10               |

    When the generate current season summary use case is executed

    Then the following current season summary is obtained
      | name          | image | id |
      | Touyama Nao   | URL14 | 1  |
      | Rikiya Koyama | URL16 | 2  |
      | Takuya Eguchi | URL18 | 3  |

    And the following current season roles are obtained for each seiyuu
      | seiyuu id | character name    | character thumbnail | series name       | series id |
      | 1         | Kongou            | URL6                | Kantai Collection | 1         |
      | 1         | Yuigahama Yui     | URL12               | Oregairu          | 3         |
      | 2         | Emiya Kiritsugu   | URL8                | Fate Zero         | 2         |
      | 3         | Hikigaya Hachiman | URL10               | Oregairu          | 3         |

    And the following all time roles are obtained for each seiyuu
      | seiyuu id | character name    | character thumbnail | series name       | series id |
      | 1         | Kongou            | URL6                | Kantai Collection | 1         |
      | 1         | Yuigahama Yui     | URL12               | Oregairu          | 3         |
      | 1         | Shima Rin         | URL21               | Yuru Camp         | 4         |
      | 1         | Kirisaki Chitoge  | URL24               | Nisekoi           | 5         |
      | 2         | Emiya Kiritsugu   | URL8                | Fate Zero         | 2         |
      | 3         | Hikigaya Hachiman | URL10               | Oregairu          | 3         |