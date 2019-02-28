import { CrudItem } from './crud.item';
import { FormItem, FormItemType,  } from './form.item';

export class HiveInspection extends CrudItem {

    public static readonly ITEM_NAMES = [
        'queenSeen',
        'eggsSeen',
        'layingPatternStars',
        'temperamentStars',
        'queenCells',
        'supersedureCells',
        'swarmCells',
        'combBuildingStars',
        'framesSealedBrood',
        'framesOpenBrood',
        'framesHoney',
        'weather',
        'temperatureF',
        'windSpeedMph',
    ].concat(CrudItem.ITEM_NAMES);

    public id: number;
    private hiveId: number;
    private queenSeen: boolean;
    private eggsSeen: boolean;
    private layingPatternStars: number;
    private temperamentStars: number;
    private queenCells: number;
    private supersedureCells: number;
    private swarmCells: number;
    private combBuildingStars: number;
    private framesSealedBrood: number;
    private framesOpenBrood: number;
    private framesHoney: number;
    private weather: string;
    private temperatureF: number;
    private windSpeedMph: number;

    constructor(hiveId: number) {
        super();
        this.hiveId = hiveId;
    }

    getFormItems(): FormItem[] {
        return [
            new FormItem('queenSeen', FormItemType.Boolean, this.queenSeen),
            new FormItem('eggsSeen', FormItemType.Boolean, this.eggsSeen),
            new FormItem('layingPatternStars', FormItemType.Stars, this.layingPatternStars),
            new FormItem('temperamentStars', FormItemType.Stars, this.temperamentStars),
            new FormItem('queenCells', FormItemType.Integer, this.queenCells),
            new FormItem('supersedureCells', FormItemType.Integer, this.supersedureCells),
            new FormItem('swarmCells', FormItemType.Integer, this.swarmCells),
            new FormItem('combBuildingStars', FormItemType.Stars, this.combBuildingStars),
            new FormItem('framesSealedBrood', FormItemType.Integer, this.framesSealedBrood),
            new FormItem('framesOpenBrood', FormItemType.Integer, this.framesOpenBrood),
            new FormItem('framesHoney', FormItemType.Integer, this.framesHoney),
            new FormItem('weather', FormItemType.String, this.weather),
            new FormItem('temperatureF', FormItemType.Integer, this.temperatureF),
            new FormItem('windSpeedMph', FormItemType.Integer, this.windSpeedMph),
        ]
    }
}