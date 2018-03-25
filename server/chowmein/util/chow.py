from util.date import DateUtil as dateutil
import datetime

class ChowUtil():

    @classmethod
    def remove_expired(cls, chow_list):
        # remove chows that are expired
        currTime = datetime.datetime.now().isoformat()
        
        # ensure date format is correct on meet time
        return [chow for chow in chow_list if not 
        ('meetTime' in chow and 
            dateutil.iso_date_valid(chow['meetTime']) and 
            chow['meetTime'] < currTime)]

    @classmethod
    def remove_joined_users(cls, chow_list):
        return [chow for chow in chow_list if 
            ('joinedUser' not in chow or 
                not chow['joinedUser'])]

    @classmethod
    def remove_soft_delete(cls, chow_list):
        return [chow for chow in chow_list if not 
        ('deleted' in chow and chow['deleted'] == 1)]
