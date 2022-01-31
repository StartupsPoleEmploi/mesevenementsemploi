import {ICandidat} from './ICandidat';
import {IPrerequisites} from './IPrerequisites';
import {IModeAcees} from './IModeAccess';
import ITag from './ITag';
import {IOffer} from './IOffer';
import IRecruiter from './IRecruiters';

export interface IEvent {
    id: number;
    codeAgence: string;
    description: string;
    deroulement: string;
    estApublier: boolean;
    dateEvenement: string;
    dateISO: Date;
    heureDebut: string;
    heureFin: string;
    statut: string;
    etatId: number;
    etatLibelle: string;

    lieu: string;
    adresse: string;
    codePostal: string;
    ville: string;
    lienPlusInformation: string;
    libelleLienPlusInformation: string;

    nafCode: number;
    objectif: ITag[];
    objectifLibelle: string;

    timeZone: string;
    titre: string;

    typeEvenementId: number;
    romeId: number;

    offres: IOffer[];
    candidats: ICandidat[];
    prerequis: IPrerequisites[];
    modeAcceesList: IModeAcees[];
    tags: ITag[];
    Offres: IOffer[];
    intervenants: IRecruiter[];

}
