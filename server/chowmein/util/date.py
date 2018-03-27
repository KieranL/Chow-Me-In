import datetime

class DateUtil():

    @classmethod
    def iso_date_valid(cls, timestamp):
        try:
            datetime.datetime.strptime(timestamp, '%Y-%m-%dT%H:%M:%S')
        except ValueError:
            return False;

        # no exceptions implies that time is valid
        return True;
