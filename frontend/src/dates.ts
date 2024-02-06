const dayNameFormatter = new Intl.DateTimeFormat('it-IT', {weekday: 'long'});
const hoursFormatter = new Intl.DateTimeFormat('it-IT', {hour: 'numeric', minute: 'numeric'});

export function getDayName(day: number) {
    const name = dayNameFormatter.format(new Date(0, 0, day));
    return name.charAt(0).toUpperCase() + name.slice(1);
}

export function timeToDate(time: string) {
    return new Date(`1970-01-01T${time}`);
}

export function formatTime(time: string) {
    return hoursFormatter.format(timeToDate(time));
}

export function formatTimeFromDate(date: Date) {
    return hoursFormatter.format(date);
}
export type CurrentOpeningResult = { open: boolean, nextOpeningTime?: Date, nextClosingTime?: Date };

export function isCurrentlyOpen(openingTimes: string[][], closingTimes: string[][]): CurrentOpeningResult {
    const now = new Date();
    const day = (now.getDay() + 6) % 7;

    const nowTime = now.getHours() * 60 + now.getMinutes();
    const openingTimesToday = openingTimes[day];
    const closingTimesToday = closingTimes[day];
    if (!openingTimesToday || !closingTimesToday || openingTimesToday.length === 0 || closingTimesToday.length === 0) {
        return {
            open: false,
        };
    }

    for (let i = 0; i < openingTimesToday.length; i++) {
        const openingTime = timeToDate(openingTimesToday[i]);
        const closingTime = timeToDate(closingTimesToday[i]);
        if (nowTime >= openingTime.getHours() * 60 + openingTime.getMinutes() && nowTime < closingTime.getHours() * 60 + closingTime.getMinutes()) {
            return {
                open: true,
                nextClosingTime: closingTime
            };
        }
    }

    let nextOpeningTime: Date;
    for (let i = 0; i < openingTimesToday.length; i++) {
        const openingTime = timeToDate(openingTimesToday[i]);
        if (nowTime < openingTime.getHours() * 60 + openingTime.getMinutes()) {
            nextOpeningTime = openingTime;
            break;
        }
    }

    return {
        open: false,
        nextOpeningTime
    };
}
